/*
 * Copyright (c) 2010-2014 Evolveum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.evolveum.midpoint.web.security;

import com.evolveum.midpoint.web.page.admin.configuration.*;
import com.evolveum.midpoint.web.page.admin.home.PageDashboard;
import com.evolveum.midpoint.web.page.admin.home.PageMyPasswords;
import com.evolveum.midpoint.web.page.admin.configuration.PageAccounts;
import com.evolveum.midpoint.web.page.admin.reports.PageReports;
import com.evolveum.midpoint.web.page.admin.resources.PageResource;
import com.evolveum.midpoint.web.page.admin.resources.PageResourceEdit;
import com.evolveum.midpoint.web.page.admin.resources.PageResourceWizard;
import com.evolveum.midpoint.web.page.admin.resources.PageResources;
import com.evolveum.midpoint.web.page.admin.resources.content.PageAccount;
import com.evolveum.midpoint.web.page.admin.resources.content.PageContentAccounts;
import com.evolveum.midpoint.web.page.admin.resources.content.PageContentEntitlements;
import com.evolveum.midpoint.web.page.admin.roles.PageRole;
import com.evolveum.midpoint.web.page.admin.roles.PageRoles;
import com.evolveum.midpoint.web.page.admin.server.PageTaskAdd;
import com.evolveum.midpoint.web.page.admin.server.PageTaskEdit;
import com.evolveum.midpoint.web.page.admin.server.PageTasks;
import com.evolveum.midpoint.web.page.admin.users.*;
import com.evolveum.midpoint.web.page.admin.workflow.*;
import com.evolveum.midpoint.web.page.login.PageLogin;
import com.evolveum.midpoint.web.util.MidPointPageParametersEncoder;
import com.evolveum.midpoint.web.util.OnePageParameterEncoder;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.IPageParametersEncoder;

import static com.evolveum.midpoint.common.security.AuthorizationConstants.*;

/**
 * @author lazyman
 */
public enum PageUrlMapping {

    LOGIN("/login", null, null, null),

    ADMIN_DASHBOARD("/admin/dashboard", null, null, new String[]{AUTZ_UI_DASHBOARD_URL, AUTZ_UI_HOME_ALL_URL}),

    ADMIN_MY_PASSWORDS("/admin/myPasswords", PageMyPasswords.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_MY_PASSWORDS_URL, AUTZ_UI_HOME_ALL_URL}),

    ADMIN_USERS("/admin/users", PageUsers.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_USERS_URL, AUTZ_UI_USERS_ALL_URL}),
    ADMIN_FIND_USERS("/admin/users/find", PageFindUsers.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_FIND_USERS_URL, AUTZ_UI_USERS_ALL_URL}),
    ADMIN_USER("/admin/user", PageUser.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_USER_URL, AUTZ_UI_USERS_ALL_URL}),
    ADMIN_USER_DETAILS("/admin/user/**", null, null, new String[]{AUTZ_UI_USER_DETAILS_URL, AUTZ_UI_USERS_ALL_URL}),
    ADMIN_USERS_BULK("/admin/users/bulk", PageBulkUsers.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_DENY_ALL_URL}),
    //fix security [lazyman]
    ORG_UNIT("/admin/org/unit", PageOrgUnit.class, new OnePageParameterEncoder(), null),
    ORG_TREE("/admin/org/tree", PageOrgTree.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_ORG_STRUCT_URL, AUTZ_UI_USERS_ALL_URL}),

    TASK("/admin/task", PageTaskEdit.class, new OnePageParameterEncoder(), new String[]{AUTZ_UI_TASK_URL, AUTZ_UI_TASKS_ALL_URL}),
    TASK_DETAILS("/admin/task/**", null, null, new String[]{AUTZ_UI_TASK_DETAIL_URL, AUTZ_UI_TASKS_ALL_URL}),
    TASKS("/admin/tasks", PageTasks.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_TASKS_URL, AUTZ_UI_TASKS_ALL_URL}),
    ADD_TASK("/admin/addTask", PageTaskAdd.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_TASK_ADD_URL, AUTZ_UI_TASKS_ALL_URL}),

    ROLE("/admin/role", PageRole.class, new OnePageParameterEncoder(), new String[]{AUTZ_UI_ROLE_URL, AUTZ_UI_ROLES_ALL_URL}),
    ROLE_DETAILS("/admin/role/**", null, null, new String[]{AUTZ_UI_ROLE_DETAILS_URL, AUTZ_UI_ROLES_ALL_URL}),
    ROLES("/admin/roles", PageRoles.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_ROLES_URL, AUTZ_UI_ROLES_ALL_URL}),

    RESOURCE("/admin/resource", PageResource.class, new OnePageParameterEncoder(), new String[]{AUTZ_UI_RESOURCE_URL, AUTZ_UI_RESOURCES_ALL_URL}),
    RESOURCE_DETAILS("/admin/resource/**", null, null, new String[]{AUTZ_UI_RESOURCE_DETAILS_URL, AUTZ_UI_RESOURCES_ALL_URL}),
    RESOURCE_EDIT("/admin/resource/edit", PageResourceEdit.class, new OnePageParameterEncoder(), new String[]{AUTZ_UI_RESOURCE_EDIT_URL, AUTZ_UI_RESOURCES_ALL_URL}),
    //todo url security for wizard
    RESOURCE_WIZARD("/admin/resource/wizard", PageResourceWizard.class, new OnePageParameterEncoder(), null),
    RESOURCES("/admin/resources", PageResources.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_RESOURCES_URL, AUTZ_UI_RESOURCES_ALL_URL}),
    RESOURCES_ACCOUNT("/admin/resources/account", PageAccount.class, new OnePageParameterEncoder(), new String[]{AUTZ_UI_RESOURCES_ACCOUNT_URL, AUTZ_UI_RESOURCES_ALL_URL}),
    RESOURCES_CONTENT_ACCOUNTS("/admin/resources/content/accounts", PageContentAccounts.class, new OnePageParameterEncoder(), new String[]{AUTZ_UI_RESOURCES_CONTENT_ACCOUNTS_URL, AUTZ_UI_RESOURCES_ALL_URL}),
    RESOURCES_CONTENT_ENTITLEMENTS("/admin/resources/content/entitlements", PageContentEntitlements.class, new OnePageParameterEncoder(), new String[]{AUTZ_DENY_ALL_URL}),

    WORK_ITEM("/admin/workItem", PageWorkItem.class, new OnePageParameterEncoder(), new String[]{AUTZ_UI_WORK_ITEM_URL, AUTZ_UI_WORK_ITEMS_ALL_URL}),
    WORK_ITEMS("/admin/workItems", PageWorkItems.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_WORK_ITEMS_URL, AUTZ_UI_WORK_ITEMS_ALL_URL}),
    WORK_ITEMS_ALL_REQUESTS("/admin/workItems/allRequests", PageProcessInstancesAll.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_WORK_ITEMS_ALL_REQUESTS_URL, AUTZ_UI_WORK_ITEMS_ALL_URL}),
    WORK_ITEMS_MY_REQUESTS("/admin/workItems/myRequests", PageProcessInstancesRequestedBy.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_WORK_ITEMS_MY_REQUESTS_URL, AUTZ_UI_WORK_ITEMS_ALL_URL}),
    WORK_ITEMS_ABOUT_ME_REQUESTS("/admin/workItems/aboutMeRequests", PageProcessInstancesRequestedFor.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_WORK_ITEMS_ABOUT_ME_REQUESTS_URL, AUTZ_UI_WORK_ITEMS_ALL_URL}),
    WORK_ITEMS_PROCESS_INSTANCE("/admin/workItems/processInstance", PageProcessInstance.class, new OnePageParameterEncoder(), new String[]{AUTZ_UI_WORK_ITEMS_PROCESS_INSTANCE_URL, AUTZ_UI_WORK_ITEMS_ALL_URL}),

    CONFIG("/admin/config", PageSystemConfiguration.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_CONFIGURATION_URL, AUTZ_UI_CONFIGURATION_ALL_URL}),
    CONFIG_DEBUG("/admin/config/debug", PageDebugView.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_CONFIGURATION_DEBUG_URL, AUTZ_UI_CONFIGURATION_ALL_URL}),
    CONFIG_DEBUGS("/admin/config/debugs", PageDebugList.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_CONFIGURATION_DEBUGS_URL, AUTZ_UI_CONFIGURATION_ALL_URL}),
    CONFIG_IMPORT("/admin/config/import", PageImportObject.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_CONFIGURATION_IMPORT_URL, AUTZ_UI_CONFIGURATION_ALL_URL}),
    CONFIG_LOGGING("/admin/config/logging", PageLogging.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_CONFIGURATION_LOGGING_URL, AUTZ_UI_CONFIGURATION_ALL_URL}),
    CONFIG_TIME_TEST("/admin/config/timeTest", PageTimeTest.class, MidPointPageParametersEncoder.ENCODER, new String[]{null}),
    CONFIG_SYSTEM_CONFIGURATION("/admin/config/system", PageSystemConfiguration.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_CONFIGURATION_SYSTEM_CONFIG_URL}),
    CONFIG_ABOUT("/admin/config/about",  null, null, new String[]{AUTZ_UI_PERMIT_ALL_URL}),
    CONFIG_SYNC_ACCOUNTS("/admin/config/sync/accounts", PageAccounts.class, MidPointPageParametersEncoder.ENCODER, null),

    REPORTS("/admin/reports", PageReports.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_REPORTS_URL}),

    ADMIN("/admin", PageDashboard.class, MidPointPageParametersEncoder.ENCODER, new String[]{AUTZ_UI_DASHBOARD_URL, AUTZ_UI_HOME_ALL_URL}),

    //todo errors will be removed later completely, they are mounted manually in MidpointApplication [lazyman]
    ERROR("/error", null, null, null),
    ERROR_401("/error/401", null, null, null),
    ERROR_403("/error/403", null, null, null),
    ERROR_404("/error/404", null, null, null);

    private String url;

    private Class<? extends WebPage> page;

    private IPageParametersEncoder encoder;

    private String[] action;

    private PageUrlMapping(String url, Class<? extends WebPage> page,
                           IPageParametersEncoder encoder, String[] action) {
        this.encoder = encoder;
        this.page = page;
        this.url = url;
        this.action = action;
    }

    public static String[] findActions(Class page) {
        for (PageUrlMapping urlMapping : values()) {
            if (page.equals(urlMapping.getPage())) {
                return urlMapping.getAction();
            }
        }
        return null;
    }

    public static Class findClassForAction(String action) {
        for (PageUrlMapping urlMapping : values()) {
            if (urlMapping.getAction() != null) {
                for (String act : urlMapping.getAction()) {
                    if (act.equals(action)) {
                        return urlMapping.getPage();
                    }
                }
            }
        }
        return null;
    }

    public IPageParametersEncoder getEncoder() {
        return encoder;
    }

    public Class<? extends WebPage> getPage() {
        return page;
    }

    public String[] getAction() {
        return action;
    }

    public String getUrl() {
        return url;
    }
}
