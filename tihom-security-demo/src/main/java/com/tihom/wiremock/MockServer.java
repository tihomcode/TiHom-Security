package com.tihom.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * @author TiHom
 */
public class MockServer {
    public static void main(String[] args) throws IOException {
        configureFor(8082);
        removeAllMappings();

        mock("/order/1","01");

    }

    private static void mock(String url, String file) throws IOException {
        ClassPathResource resource = new ClassPathResource("mock/response/01.txt");
        String content = StringUtils.join(FileUtils.readLines(resource.getFile(),"UTF-8").toArray(),"\n");

        stubFor(get(urlPathEqualTo("/order/1"))
                .willReturn(aResponse()
                .withBody(content)
                .withStatus(200)));
    }
}
