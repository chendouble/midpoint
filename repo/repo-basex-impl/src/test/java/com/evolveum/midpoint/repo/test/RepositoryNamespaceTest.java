/*
 * Copyright (c) 2011 Evolveum
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * http://www.opensource.org/licenses/cddl1 or
 * CDDLv1.0.txt file in the source code distribution.
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 *
 * Portions Copyrighted 2011 lazyman
 * 
 */
package com.evolveum.midpoint.repo.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import com.evolveum.midpoint.common.result.OperationResult;
import com.evolveum.midpoint.repo.api.RepositoryService;
import com.evolveum.midpoint.schema.processor.Schema;
import com.evolveum.midpoint.util.DOMUtil;
import com.evolveum.midpoint.util.logging.Trace;
import com.evolveum.midpoint.util.logging.TraceManager;
import com.evolveum.midpoint.xml.ns._public.common.common_1.ConnectorType;
import com.evolveum.midpoint.xml.ns._public.common.common_1.PropertyReferenceListType;
import com.evolveum.midpoint.xml.ns._public.common.common_1.XmlSchemaType;

/**
 * 
 * @author lazyman
 * 
 */
@ContextConfiguration(locations = { "../../../../../application-context-repository.xml",
		"classpath:application-context-configuration-test.xml" })
public class RepositoryNamespaceTest extends AbstractTestNGSpringContextTests {

	private static final Trace LOGGER = TraceManager.getTrace(RepositoryNamespaceTest.class);
	@Autowired(required = true)
	private RepositoryService repository;

	/**
	 * This test deserialize schema object from files located in serialized
	 * folder. Than we create sample connector type object which we try to save
	 * in repository. Save is successful but, namespaces in there are messed up.
	 * They were moved from xsd:schema element (not root c:connector) to root
	 * c:connector. XSD schema in connector object contains attributes with
	 * QName value. But when we get connector object from repository, BaseX or
	 * JAXB doesn't know that it have to create namespace definition in
	 * xsd:schema element for that QName attributes. Therefore prefixes in
	 * QNames in attributes can't be resolved.
	 * 
	 * Note: Test maintainability is very difficult, because if we change classes definition we have to regenerate files in serialized folder
	 * 
	 * @throws Exception
	 */
	@Test(enabled=false)
	public void serializedTest() throws Exception {
		URL url = RepositoryNamespaceTest.class.getClassLoader().getResource("serialized");
		File folder = new File(url.toURI());
		if (!folder.exists() || !folder.isDirectory()) {
			return;
		}
		File[] files = folder.listFiles();
		if (files == null) {
			return;
		}

		for (File file : files) {
			OperationResult result = new OperationResult("Testing namespaces");
			try {
				LOGGER.debug("Deserializing file {}", file.getName());
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
				Schema schema = (Schema) in.readObject();
				in.close();

				LOGGER.debug("Parsing schema to dom (dom in trace).");
				Document document = schema.serializeToXsd();
				String xml = DOMUtil.printDom(document).toString();
				LOGGER.trace("Schema parsed to dom:\n{}", xml);

				// connector object just for testing...
				ConnectorType connector = new ConnectorType();
				XmlSchemaType xmlSchema = new XmlSchemaType();
				
				document = DOMUtil.parseDocument(xml);				
				xmlSchema.getAny().add(document.getDocumentElement());
				
				connector.setSchema(xmlSchema);
				connector.setName(file.getName());
				connector.setNamespace("http://" + file.getName());

				LOGGER.debug("Adding connector to repository");
				String oid = repository.addObject(connector, result);

				connector = repository.getObject(ConnectorType.class, oid, new PropertyReferenceListType(),
						result);
				xmlSchema = connector.getSchema();
				LOGGER.debug("Parsing dom schema to object");
				schema = Schema.parse(xmlSchema.getAny().get(0));
				LOGGER.debug("Schema parsed {}", schema);
				document = schema.serializeToXsd();
				xml = DOMUtil.printDom(document).toString();
				LOGGER.trace("Schema parsed to dom:\n{}", xml);
				document = DOMUtil.parseDocument(xml);
				schema = Schema.parse(document.getDocumentElement());
			} catch (Exception ex) {
				Assert.fail(ex.getMessage(), ex);
			} finally {
				LOGGER.debug(result.dump());
			}
		}
	}
}
