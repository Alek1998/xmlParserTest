package com.company.helpers;

import com.company.domain.Student;
import com.company.helpers.api.StudentDataProcessor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

/**
 * Created by alek.aleksanyan on 1/27/2017.
 */
public class StudentDataXMLProcessor implements StudentDataProcessor {
    @Override
    public boolean save(List<Student> students, File outputFile) {
        try {
            Objects.requireNonNull(outputFile, "The outputFile parameter should not be null.");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element rootElement = doc.createElement("class");
            doc.appendChild(rootElement);
            Element students1 = doc.createElement("students");
            rootElement.appendChild(students1);
            for (Student student : students) {
                Element currentStudent = doc.createElement("student");
                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(String.valueOf(student.getId())));
                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(student.getName()));
                Element age = doc.createElement("age");
                age.appendChild(doc.createTextNode(String.valueOf(student.getAge())));
                Element course = doc.createElement("course");
                course.appendChild(doc.createTextNode(student.getCourse()));
                currentStudent.appendChild(id);
                currentStudent.appendChild(name);
                currentStudent.appendChild(age);
                currentStudent.appendChild(course);
                students1.appendChild(currentStudent);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(outputFile);
            transformer.transform(source, result);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public List<Student> load(File sourceFile) {
        List<Student> students = new ArrayList<>();
        try {
            Objects.requireNonNull(sourceFile, "The inputFile parameter should not be null.");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(sourceFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("student");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    students.add(new Student(
                            Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent()),
                            eElement.getElementsByTagName("name").item(0).getTextContent(),
                            Integer.parseInt(eElement.getElementsByTagName("age").item(0).getTextContent()),
                            eElement.getElementsByTagName("course").item(0).getTextContent()
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}
