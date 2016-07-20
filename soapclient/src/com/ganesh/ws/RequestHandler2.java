/*package com.ganesh.ws;



import java.io.FileNotFoundException;
import java.io.IOException;
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
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;





*//**
 * This class is used to sign the signature to request and handle response
 * 
 * @author TCS
 * 
 *//*
public class RequestHandler2 implements SOAPHandler<SOAPMessageContext> {
	Logger LOGGER = Logger.getLogger(RequestHandler2.class.getName());
	private static String KEYSTORE = null;
	private static String PASSWORD = null;
	private static String KEY_ALIAS = null;

	*//**
	 * Handle message
	 *//*
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

	*//**
	 * Retrieve response
	 * 
	 * @param message
	 * @param soapPart
	 *//*
	private void getResponse(SOAPMessage message, SOAPPart soapPart) {
		try {
			message.writeTo(System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
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
	 *//*
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

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
		  DigestMethod digestMethod = fac.newDigestMethod 
		       ("http://www.w3.org/2000/09/xmldsig#sha1", null);
		  Reference ref = fac.newReference("#10",digestMethod);
		  ArrayList refList = new ArrayList();
		  refList.add(ref);
		  CanonicalizationMethod cm =  fac.newCanonicalizationMethod("http://www.w3.org/2001/10/xml-exc-c14n#",(C14NMethodParameterSpec) null);
		  SignatureMethod sm = fac.newSignatureMethod( 
		       "http://www.w3.org/2000/09/xmldsig#rsa-sha1",null);
		  SignedInfo signedInfo =fac.newSignedInfo(cm,sm,refList);
		  DOMSignContext signContext = null;
		 signContext = new DOMSignContext(privateKey,headerElement);
		 signContext.setURIDereferencer(new URIDereferencer());
		 KeyInfoFactory keyFactory = KeyInfoFactory.getInstance();
		 DOMStructure domKeyInfo = new DOMStructure(tokenReference);
		 Node tokenReference = 
		 KeyInfo keyInfo = 
		       keyFactory.newKeyInfo(Collections.singletonList(domKeyInfo));
		 XMLSignature signature = fac.newXMLSignature(signedInfo,keyInfo)

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
		XMLSignature signature = signFactory.newXMLSignature(signedInfo, ki);
		// Marshal, generate, and sign the enveloped signature.
		signature.sign(dsc);
		
		dumpDocument(root);
		
		
		String xml = IOUtils.toString(new FileInputStream(new File("E:\\Ganesh\\Java\\jks\\cxf-xml-signature\\sopa-request.xml")));		
		source = new StreamSource(new StringReader(xml));
		soapPart.setContent(source);
		message.saveChanges();
	}

	*//**
	 * Get the certificate
	 * 
	 * @param alias
	 * @param ks
	 * @return
	 * @throws KeyStoreException
	 *//*
	private static X509Certificate getCertificate(String alias, KeyStore ks) throws KeyStoreException {
		X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
		return cert;
	}

	*//**
	 * Log the document
	 * 
	 * @param root
	 * @throws TransformerException
	 *//*
	private static void dumpDocument(Node root) throws TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(new DOMSource(root), new StreamResult(System.out));
		System.out.println("-----------------------------------------");
		System.out.println("-----------------------------------------");
	}

	*//**
	 * Get first child element
	 * 
	 * @param node
	 * @return Element
	 *//*
	private static Element getFirstChildElement(Node node) {
		Node child = node.getFirstChild();
		while ((child != null) && (child.getNodeType() != Node.ELEMENT_NODE)) {
			child = child.getNextSibling();
		}
		return (Element) child;
	}

	*//**
	 * 
	 * @param node
	 * @return
	 *//*
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

	*//**
	 * Handle SOAP fault
	 *//*
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
*/