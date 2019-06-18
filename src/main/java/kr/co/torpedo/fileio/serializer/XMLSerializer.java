package kr.co.torpedo.fileio.serializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import kr.co.torpedo.fileio.Serializable;
import kr.co.torpedo.fileio.model.Intern;

public class XMLSerializer extends Serializer {
	public XMLSerializer() {
		setFileName("sawon-v1.xml");
		setFileNameIntern("sawon-v2.xml");
	}

	@Override
	protected void selialize() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			writeEmployee(docBuilder);
		} catch (ParserConfigurationException e) {
			Serializable.invalidFileLogger.error("XMLSerializer ParserConfigurationException : " + e);
		}
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
				Serializable.invalidFileLogger.error("XMLSerializer InvalidClassException : " + e);
			}
		}
		// 루트 엘리먼트
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("company");
		doc.appendChild(rootElement);
		Element employee = null;

		for (int i = 0; i < getEmployeeList().size(); i++) {
			// employee 엘리먼트
			employee = doc.createElement("employee");
			rootElement.appendChild(employee);

			// 속성값 정의
			employee.setAttribute("id", Integer.toString(i + 1));

			// name 엘리먼트
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(getEmployeeList().get(i).getName()));
			employee.appendChild(name);

			// age 엘리먼트
			Element age = doc.createElement("age");
			age.appendChild(doc.createTextNode(Integer.toString(getEmployeeList().get(i).getAge())));
			employee.appendChild(age);

			// phoneNumber 엘리먼트
			Element phoneNumber = doc.createElement("phoneNumber");
			phoneNumber.appendChild(doc.createTextNode(getEmployeeList().get(i).getPhoneNumber()));
			employee.appendChild(phoneNumber);

			// department 엘리먼트
			Element department = doc.createElement("department");
			department.appendChild(doc.createTextNode(getEmployeeList().get(i).getDepartMent()));
			employee.appendChild(department);

			// email 엘리먼트
			Element email = doc.createElement("email");
			email.appendChild(doc.createTextNode(getEmployeeList().get(i).getEmail()));
			employee.appendChild(email);

			// term 엘리먼트
			Element term = doc.createElement("term");
			String str;
			if (getEmployeeList().get(i) instanceof Intern) {
				str = Integer.toString(((Intern) getEmployeeList().get(i)).getTerm());
			} else {
				str = "정직원";
			}
			term.appendChild(doc.createTextNode(str));
			employee.appendChild(term);
		}

		try {
			// XML 파일로 쓰기
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);
			StreamResult result = null;
			try {
				result = new StreamResult(new FileOutputStream(getMakefile()));
			} catch (FileNotFoundException e) {
				Serializable.invalidFileLogger.error("XMLSerializer FileNotFoundException : " + e);
			} finally {
				try {
					transformer.transform(source, result);
				} catch (TransformerException e) {
					Serializable.invalidFileLogger.error("XMLSerializer TransformerException : " + e);
				}
			}
		} catch (TransformerConfigurationException e) {
			Serializable.invalidFileLogger.error("XMLSerializer TransformerConfigurationException : " + e);
		}
	}
}
