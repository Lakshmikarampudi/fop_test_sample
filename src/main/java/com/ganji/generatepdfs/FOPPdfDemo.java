package com.ganji.generatepdfs;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.TimeZone;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


public class FOPPdfDemo {
	
	

	public static void main(String[] args) throws IOException {
		FOPPdfDemo fOPPdfDemo = new FOPPdfDemo();
		long timeStamp = System.currentTimeMillis();
		String xml = fOPPdfDemo.parseThymeLeayTemplate("Lakshmi Testing");
		fOPPdfDemo.convertToPDF(xml, timeStamp);
	
	}
	public static final String CONFIGURATION_XML = "configuration.xconf";

	
	public void convertToPDF(String xml,long timeStamp) throws IOException{
		OutputStream out = null;
		
		try{
			FopFactory fopFactory = FopFactory.newInstance(this.getClass().getClassLoader().getResource(CONFIGURATION_XML).toURI());
			String outPutPath = "C://Users//Lakshmi//OneDrive//Documents//LogData" + "/" + "Lakshmi" + "-" + timeStamp
					+ ".pdf";
			File outPutFolder = new File(outPutPath);
			outPutFolder.getParentFile().mkdir();
			FileOutputStream outputStream = new FileOutputStream(outPutFolder);
			out = new BufferedOutputStream(outputStream);
			 Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
	            Source src = new StreamSource(new StringReader(xml));
	            Result result = new SAXResult(fop.getDefaultHandler());
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
				/*
				 * transformerFactory.setFeature(DTD_VALIDATION, FALSE);
				 * transformerFactory.setFeature(ALLOW_EXTERNAL_FUNCTIONS, FALSE);
				 * transformerFactory.setAttribute(ALLOWED_PROTOCOLS, "");
				 */
	            transformerFactory.newTransformer().transform(src, result);
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			out.close();
		}
		
	}
	
	public String parseThymeLeayTemplate(String name) {
		
		TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setSuffix(".xml");
		templateResolver.setTemplateMode(TemplateMode.XML);
		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		
		Context context = new Context();
		
		context.setVariable("name", "Just Testing");
		
		String xml = templateEngine.process("template", context);
		System.out.println("xml:: "+xml);
		return xml;
	}
	
	

}