/*
 * Copyright (c) 2010-2015 Evolveum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.evolveum.midpoint.repo.sql.query2.definition;

import com.evolveum.midpoint.prism.Visitor;
import com.evolveum.midpoint.prism.path.ItemPath;
import com.evolveum.midpoint.repo.sql.query2.DefinitionSearchResult;

import javax.xml.namespace.QName;

/**
 * @author lazyman
 */
public class JpaAnyDefinition extends JpaItemDefinition {

    public JpaAnyDefinition(QName jaxbName, String jpaName, Class jpaClass) {
        super(jaxbName, jpaName, null, jpaClass);      // no collection specification for this kind of item
    }

    @Override
    protected String getDebugDumpClassName() {
        return "Any";
    }

    @Override
    public DefinitionSearchResult nextDefinition(ItemPath path) {
        // There is nothing we can do here. Return the definition itself, and
        // the path as to be found within the appropriate Any container.
        // Hoping the client will understand this and won't cycle while finding
        // the definition. ;)
        return new DefinitionSearchResult(this, path);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    protected void debugDumpExtended(StringBuilder sb, int indent) {
        // nothing to put here
    }
}