package kr.co.torpedo.fileio.deserializer;

import java.io.IOException;
import java.io.InvalidClassException;

import javax.naming.InvalidNameException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import kr.co.torpedo.fileio.Serializable;
import kr.co.torpedo.fileio.model.Employee;
import kr.co.torpedo.fileio.model.Intern;

public class XMLDeSerializer extends DeSerializer {
	public XMLDeSerializer() {
		setFileName("sawon-v1.xml");
		setFileNameIntern("sawon-v2.xml");
	}

	@Override
	public void deSelialize() {
		try {
			// XML 문서 파싱
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = null;
			Employee emp = null;
			try {
				document = documentBuilder.parse(getMakefile().getAbsolutePath());
				readEmployee(emp, document);
			} catch (SAXException | IOException e) {
				Serializable.invalidFileLogger.error("XMLDeSerializer Exception : " + e);
			}
		} catch (ParserConfigurationException e) {
			Serializable.invalidFileLogger.error("XMLDeSerializer Exception : " + e);
		}
	}

	@Override
	public void readEmployee(Employee emp, Object obj) {
		Document document = null;
		if (obj instanceof Document) {
			document = (Document) obj;
		} else {
			try {
				throw new InvalidClassException("Document 클래스가 아님");
			} catch (InvalidClassException e) {
				Serializable.invalidFileLogger.error("XMLDeSerializer Exception : " + e);
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
						Serializable.invalidFileLogger.error("XMLDeSerializer Exception : " + e);
					}
				}
			}
		}
	}

	public void parsingXmlData(NodeList nodeChildren) {
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
			addList(employee);
		} else {
			int termInt = Integer.parseInt(term);
			Intern intern = new Intern(name, age, phoneNumber, department, email, termInt);
			addList(intern);
		}
	}
}
