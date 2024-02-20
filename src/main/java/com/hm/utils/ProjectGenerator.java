package com.hm.utils;



import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ProjectGenerator {

    public static byte[] generateZipFile(ProjectRequest request)throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos);
        ) {
            addFileToZip(zos, "pom.xml", generatePomxml(request));
            zos.finish();
            return baos.toByteArray();
        }
    }
    private static String generatePomxml(ProjectRequest request) throws IOException {
        InputStream is= ProjectGenerator.class.getResourceAsStream("/resources/pom.xml.ft1");
        String template= IOUtils.toString(is,StandardCharsets.UTF_8);
        template=template.replace("${groupId}",request.getGroupId())
                .replace("${artifactId}",request.getArtifactId())
                .replace("${name}",request.getName())
                .replace("${description}",request.getDescription())
                .replace("${packaging}",request.getPackaging())
                .replace("${dependencies}",generateDependencies(request.getDependencies()));

       return template;
    }

    private static String generateDependencies(List<String> dependencies){
        StringBuilder sb=new StringBuilder();
        for(String dependency : dependencies){
            sb.append("<dependency>\n");
            sb.append(" <groupId>").append(dependency).append("</groupId>\n");
            sb.append(" <artifactId>").append(dependency).append("</artifactId>\n");
            sb.append(" <version>").append(dependency).append("</version>\n");
            sb.append(" <packaging>").append(dependency).append("</packaging>\n");
            sb.append("</dependency>\n");
        }
        return sb.toString();
    }
    private static void addFileToZip(ZipOutputStream zos, String fileName, String content) throws IOException {
        zos.putNextEntry(new ZipEntry(fileName));
        zos.write(content.getBytes(StandardCharsets.UTF_8));
        zos.closeEntry();
    }
}
