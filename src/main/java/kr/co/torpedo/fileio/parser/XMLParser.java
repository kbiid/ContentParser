package kr.co.torpedo.fileio.parser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;

import javax.naming.InvalidNameException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import kr.co.torpedo.fileio.domain.Employee;
import kr.co.torpedo.fileio.domain.Intern;

public class XMLParser extends Parser {
	@Override
	public void selialize() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			writeEmployee(docBuilder);
		} catch (ParserConfigurationException e) {
			Parser.invalidFileLogger.error("XMLSerializer ParserConfigurationException : " + e);
		}
		dataManager.getEmployeeList().clear();
	}

	@Override
	protected void writeEmployee(Object obj) {
		DocumentBuilder docBuilder = null;

		if (obj instanceof DocumentBuilder) {
			docBuilder = (DocumentBuilder) obj;
		} else {
			try {
				throw new InvalidClassException("DocumentBuilder 클래스가 아님");
			} catch (InvalidClassException e) {
				Parser.invalidFileLogger.error("XMLSerializer InvalidClassException : " + e);
			}
		}
		Document doc = makeDocument(docBuilder);
		writeFileToXML(doc);
	}

	private void writeFileToXML(Document doc) throws TransformerFactoryConfigurationError {
		try {
			// XML 파일로 쓰기
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);
			StreamResult result = null;
			try {
				result = new StreamResult(new FileOutputStream(fileManager.getResultMadeFile()));
			} catch (FileNotFoundException e) {
				Parser.invalidFileLogger.error("XMLSerializer FileNotFoundException : " + e);
			} finally {
				try {
					transformer.transform(source, result);
				} catch (TransformerException e) {
					Parser.invalidFileLogger.error("XMLSerializer TransformerException : " + e);
				}
			}
		} catch (TransformerConfigurationException e) {
			Parser.invalidFileLogger.error("XMLSerializer TransformerConfigurationException : " + e);
		}
	}

	private Document makeDocument(DocumentBuilder docBuilder) {
		// 루트 엘리먼트
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("company");
		doc.appendChild(rootElement);
		Element employee = null;

		for (int i = 0; i < dataManager.getEmployeeList().size(); i++) {
			// employee 엘리먼트
			employee = doc.createElement("employee");
			rootElement.appendChild(employee);

			// 속성값 정의
			employee.setAttribute("id", Integer.toString(i + 1));

			// name 엘리먼트
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(dataManager.getEmployeeList().get(i).getName()));
			employee.appendChild(name);

			// age 엘리먼트
			Element age = doc.createElement("age");
			age.appendChild(doc.createTextNode(Integer.toString(dataManager.getEmployeeList().get(i).getAge())));
			employee.appendChild(age);

			// phoneNumber 엘리먼트
			Element phoneNumber = doc.createElement("phoneNumber");
			phoneNumber.appendChild(doc.createTextNode(dataManager.getEmployeeList().get(i).getPhoneNumber()));
			employee.appendChild(phoneNumber);

			// department 엘리먼트
			Element department = doc.createElement("department");
			department.appendChild(doc.createTextNode(dataManager.getEmployeeList().get(i).getDepartMent()));
			employee.appendChild(department);

			// email 엘리먼트
			Element email = doc.createElement("email");
			email.appendChild(doc.createTextNode(dataManager.getEmployeeList().get(i).getEmail()));
			employee.appendChild(email);

			// term 엘리먼트
			Element term = doc.createElement("term");
			String str;
			if (dataManager.getEmployeeList().get(i) instanceof Intern) {
				str = Integer.toString(((Intern) dataManager.getEmployeeList().get(i)).getTerm());
			} else {
				str = "정직원";
			}
			term.appendChild(doc.createTextNode(str));
			employee.appendChild(term);
		}
		return doc;
	}

	@Override
	public void deSelialize() {
		dataManager.getEmployeeList().clear();
		try {
			// XML 문서 파싱
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = null;
			Employee emp = null;
			try {
				document = documentBuilder.parse(fileManager.getResultMadeFile().getAbsolutePath());
				readEmployee(emp, document);
			} catch (SAXException | IOException e) {
				Parser.invalidFileLogger.error("XMLDeSerializer Exception : " + e);
			}
		} catch (ParserConfigurationException e) {
			Parser.invalidFileLogger.error("XMLDeSerializer Exception : " + e);
		}
	}

	@Override
	protected void readEmployee(Employee emp, Object obj) {
		Document document = null;
		if (obj instanceof Document) {
			document = (Document) obj;
		} else {
			try {
				throw new InvalidClassException("Document 클래스가 아님");
			} catch (InvalidClassException e) {
				Parser.invalidFileLogger.error("XMLDeSerializer Exception : " + e);
			}
		}
		// root 구하기
		Element root = document.getDocumentElement();

		NodeList children = root.getChildNodes(); // 자식 노드 목록 get
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) { // 해당 노드의 종류 판정(Element일 때)
				Element ele = (Element) node;
				String nodeName = ele.getNodeName();
				if (nodeName.equals("employee")) {
					NodeList nodeChildren = ele.getChildNodes();
					parsingXmlData(nodeChildren);
				} else {
					try {
						throw new InvalidNameException("노드 이름이 올바르지 않습니다.");
					} catch (InvalidNameException e) {
						Parser.invalidFileLogger.error("XMLDeSerializer Exception : " + e);
					}
				}
			}
		}
	}

	private void parsingXmlData(NodeList nodeChildren) {
		String name = null, phoneNumber = null, email = null, department = null, term = null;
		int age = 0;
		for (int j = 0; j < nodeChildren.getLength(); j++) {
			Node nodeitem = nodeChildren.item(j);
			if (nodeitem.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = nodeitem.getNodeName();
				switch (nodeName) {
				case "name":
					name = nodeitem.getTextContent();
					break;
				case "age":
					age = Integer.parseInt(nodeitem.getTextContent());
					break;
				case "phoneNumber":
					phoneNumber = nodeitem.getTextContent();
					break;
				case "department":
					department = nodeitem.getTextContent();
					break;
				case "email":
					email = nodeitem.getTextContent();
					break;
				case "term":
					term = nodeitem.getTextContent();
					break;
				default:
					break;
				}
			}
		}
		if (term.equals("정직원")) {
			Employee employee = new Employee(name, age, phoneNumber, department, email);
			dataManager.addList(employee);
		} else {
			int termInt = Integer.parseInt(term);
			Intern intern = new Intern(name, age, phoneNumber, department, email, termInt);
			dataManager.addList(intern);
		}
	}

	@Override
	public void setFileName(String fileName) {
		fileManager.setFileName(fileName + ".xml");
	}
}
