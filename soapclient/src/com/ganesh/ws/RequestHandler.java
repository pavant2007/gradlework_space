package com.ganesh.ws;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;





/**
 * This class is used to sign the signature to request and handle response
 * 
 * @author TCS
 * 
 */
public class RequestHandler implements SOAPHandler<SOAPMessageContext> {
	Logger LOGGER = Logger.getLogger(RequestHandler.class.getName());
	private static String KEYSTORE = null;
	private static String PASSWORD = null;
	private static String KEY_ALIAS = null;

	/**
	 * Handle message
	 */
	public boolean handleMessage(SOAPMessageContext context) {
		LOGGER.info("Begin RequestResponseHandler.");
		SOAPMessage message = context.getMessage();
		SOAPPart soapPart = message.getSOAPPart();

		
		KEYSTORE = "E:\\Ganesh\\Java\\jks\\test\\privatestore.jks";
		PASSWORD = "samba123";
		KEY_ALIAS = "myAlias";
		LOGGER.info("Keystore :" + KEYSTORE);

		Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (isRequest) {
			try {
				LOGGER.info("Create request with signature.");
				createReqWithSignature(message, soapPart);
				message.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "utf-8");
			} catch (Exception e) {
				LOGGER.warning("Error creating the message with signature :" + e);
			}
		} else {
			LOGGER.info("Received response");
			getResponse(message, soapPart);
		}
		return true;
	}

	/**
	 * Retrieve response
	 * 
	 * @param message
	 * @param soapPart
	 */
	private void getResponse(SOAPMessage message, SOAPPart soapPart) {
		try {
			message.writeTo(System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the request with signature
	 * 
	 * @param message
	 * @param soapPart
	 * @throws SOAPException
	 * @throws KeyStoreException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws UnrecoverableKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws MarshalException
	 * @throws XMLSignatureException
	 * @throws TransformerException
	 */
	private void createReqWithSignature(SOAPMessage message, SOAPPart soapPart) throws SOAPException, KeyStoreException, FileNotFoundException,
			IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, InvalidAlgorithmParameterException,
			ParserConfigurationException, SAXException, MarshalException, XMLSignatureException, TransformerException {

		SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		SOAPHeader soapHeader = message.getSOAPHeader();
		SOAPHeaderElement headerElement = soapHeader.addHeaderElement(soapEnvelope.createName("Security", "wsse",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"));

		headerElement.addNamespaceDeclaration("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

		SOAPBody soapBody = soapEnvelope.getBody();
		soapBody.addAttribute(soapEnvelope.createName("id", "wsu", "http://schemas.xmlsoap.org/soap/security/2000-12"), "Body");

		KeyStore ks = KeyStore.getInstance("JKS");
		java.io.FileInputStream fis = new java.io.FileInputStream(KEYSTORE);
		ks.load(fis, PASSWORD.toCharArray());

		Enumeration<?> e = ks.aliases();

		while (e.hasMoreElements()) {
			KEY_ALIAS = (String) e.nextElement();
		}

		LOGGER.info("KEY_ALIAS :" + KEY_ALIAS);
		X509Certificate clientCert = getCertificate(KEY_ALIAS, ks);

		PrivateKey privateKey = (PrivateKey) ks.getKey(KEY_ALIAS, PASSWORD.toCharArray());

		// 1. Create a DOM XMLSignatureFactory that will be used to
		// generate the enveloped signature.
		XMLSignatureFactory signFactory = XMLSignatureFactory.getInstance("DOM");

		// 2.Create a Reference to the enveloped document (in this case,
		// you are signing the whole document, so a URI of "" signifies
		// that, and also specify the SHA1 digest algorithm and
		// the ENVELOPED Transform.
		Reference ref = signFactory.newReference("#Body", signFactory.newDigestMethod(DigestMethod.SHA1, null), Collections.singletonList(signFactory
				.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)), null, null);

		// 3. Create the SignedInfo.
		SignedInfo si = signFactory.newSignedInfo(signFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
				(C14NMethodParameterSpec) null), signFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));
		
		KeyInfoFactory kif = signFactory.getKeyInfoFactory();
		List x509Content = new ArrayList();

		X509IssuerSerial xs1 = kif.newX509IssuerSerial(clientCert.getIssuerX500Principal().getName(), clientCert.getSerialNumber());
		
		
		// x509Content.add(serverCert.getSubjectX500Principal().getName());
		// x509Content.add(serverCert);
		// x509Content.add(serverCert.getIssuerX500Principal().getName());
		x509Content.add(xs1);
		
		X509Data xd = kif.newX509Data(x509Content);
		
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));
		
		
		

		Source source = soapPart.getContent();
		Node root = null;
		if (source instanceof DOMSource) {
			root = ((DOMSource) source).getNode();
		} else if (source instanceof SAXSource) {
			LOGGER.info("Create Builder..");
			InputSource inSource = ((SAXSource) source).getInputSource();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(inSource);
			root = (Node) doc.getDocumentElement();
		}
		
		Element envelope = getFirstChildElement(root);
		Element header = getFirstChildElement(envelope);
		Element security = getLastChildElement(header);
		DOMSignContext dsc = new DOMSignContext(privateKey, security);
		dsc.putNamespacePrefix(XMLSignature.XMLNS, "ds");		
		dsc.setIdAttributeNS(getNextSiblingElement(header), "http://schemas.xmlsoap.org/soap/security/2000-12", "id");

		// Create the XMLSignature, but don't sign it yet.
		XMLSignature signature = signFactory.newXMLSignature(si, ki);
		// Marshal, generate, and sign the enveloped signature.
		signature.sign(dsc);
		
		dumpDocument(root);
		
		
		String xml = IOUtils.toString(new FileInputStream(new File("E:\\Ganesh\\Java\\jks\\cxf-xml-signature\\sopa-request.xml")));		
		source = new StreamSource(new StringReader(xml));
		soapPart.setContent(source);
		message.saveChanges();
	}

	/**
	 * Get the certificate
	 * 
	 * @param alias
	 * @param ks
	 * @return
	 * @throws KeyStoreException
	 */
	private static X509Certificate getCertificate(String alias, KeyStore ks) throws KeyStoreException {
		X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
		return cert;
	}

	/**
	 * Log the document
	 * 
	 * @param root
	 * @throws TransformerException
	 */
	private static void dumpDocument(Node root) throws TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		//transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(new DOMSource(root), new StreamResult(System.out));
		System.out.println("-----------------------------------------");
		System.out.println("-----------------------------------------");
	}

	/**
	 * Get first child element
	 * 
	 * @param node
	 * @return Element
	 */
	private static Element getFirstChildElement(Node node) {
		Node child = node.getFirstChild();
		while ((child != null) && (child.getNodeType() != Node.ELEMENT_NODE)) {
			child = child.getNextSibling();
		}
		return (Element) child;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public static Element getNextSiblingElement(Node node) {
		Node sibling = node.getNextSibling();
		while ((sibling != null) && (sibling.getNodeType() != Node.ELEMENT_NODE)) {
			sibling = sibling.getNextSibling();
		}
		return (Element) sibling;
	}

	private static Element getLastChildElement(Node node) {
		Node child = node.getLastChild();
		while ((child != null) && (child.getNodeType() != Node.ELEMENT_NODE)) {
			child = child.getNextSibling();
		}
		return (Element) child;
	}

	/**
	 * Handle SOAP fault
	 */
	public boolean handleFault(SOAPMessageContext context) {
		try {
			SOAPMessage message = context.getMessage();
			message.writeTo(System.out);
			LOGGER.warning("Handle Fault :");
		} catch (Exception e) {
			LOGGER.warning("Error in handle fault :" + e);
		}
		return false;
	}

	public void close(MessageContext context) {

	}
	
	public Set<QName> getHeaders() {
		// Added this for signatureMisUnderstood Issue while getting the response
		LOGGER.warning("getHeaders ........ :");
        Set<QName> headers = new HashSet<QName>(); 
        headers.add(new QName("http://schemas.xmlsoap.org/soap/security/2000-12", "Signature"));   
        headers.add(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd","Security"));         
		return headers;
	}
	
}
