package com.dataserver.admin;

import com.dataserver.admin.rest.UserService;
import com.dataserver.admin.security.JwtAuthenticationRequest;
import com.dataserver.admin.security.User;
import com.dataserver.admin.sys.SysService;
import com.dataserver.admin.sys.Tenant;
import com.dataserver.admin.sys.TenantUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by gongxinyi on 2018/12/5.
 */
@Slf4j
@RunWith(SpringRunner.class)
//@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class AbstractTest {
    private String snippetDir = "target/generated-snippets";
    private String outputDir = "target/asciidoc";
    private String indexDoc = "docs/asciidoc/index.adoc";
    public ObjectMapper mapper = new ObjectMapper();

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    SysService sysService;
    @Autowired
    UserService userService;

    @Test
    public void initUser() {
        Tenant tenant = new Tenant();
        tenant.setId("tenant1");
        tenant.setConnectionStr("mongodb://tenant1:tenant1@ds145356.mlab.com:45356/tenant1");
        sysService.getSysDataStore().save(tenant);

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        addTenantUser(tenant, user);

        userService.initUserAndRole(tenant, user);
    }

//        @After
//    public void Test() throws Exception {
//        // 得到swagger.json,写入outputDir目录中
//        mockMvc.perform(get("/v2/dataapi-docs").accept(MediaType.APPLICATION_JSON))
//                .andDo(SwaggerResultHandler.outputDirectory(outputDir).build())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        // 读取上一步生成的swagger.json转成asciiDoc,写入到outputDir
//        // 这个outputDir必须和插件里面<generated></generated>标签配置一致
//        Swagger2MarkupConverter.from(outputDir + "/swagger.json")
//                .withPathsGroupedBy(GroupBy.TAGS)// 按tag排序
//                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)// 格式
//                .withExamples(snippetDir)
//                .build()
//                .intoFolder(outputDir);// 输出
//    }
    public void addTenantUser(Tenant tenant, User user) {
        TenantUser tenantUser = new TenantUser();
        tenantUser.setTenant(tenant);
        tenantUser.setUsername(user.getUsername());
        sysService.getSysDataStore().save(tenantUser);
    }

    protected ResultActions login(String username, String password) throws Exception {
        final JwtAuthenticationRequest auth = new JwtAuthenticationRequest();
        auth.setUsername(username);
        auth.setPassword(password);
        return mockMvc.perform(
                post("/auth")
                        .content(json(auth))
                        .contentType(MediaType.APPLICATION_JSON));
    }

    protected String json(Object o) throws IOException {
        return mapper.writeValueAsString(o);
    }

    protected String extractToken(MvcResult result) throws UnsupportedEncodingException {
        return JsonPath.read(result.getResponse().getContentAsString(), "$.token");
    }
}
