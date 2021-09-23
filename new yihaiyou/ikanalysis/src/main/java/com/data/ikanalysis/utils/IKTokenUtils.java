package com.data.ikanalysis.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.StringReader;

public class IKTokenUtils {
	private final static Analyzer	analyzer	= new IKAnalyzer(false);

	public static String token(String content) throws Exception {
		TokenStream stream = analyzer.tokenStream(null, new StringReader(content));
		CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
		stream.reset();
		StringBuilder sb = new StringBuilder();
		while (stream.incrementToken()) {
			if (sb.length() > 0) {
				sb.append(" ").append(term.toString());
			} else {
				sb.append(term.toString());
			}
		}
		stream.close();
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(IKTokenUtils.token("左权墓中华人民共和国今天股市很糟糕"));
		System.out.println(IKTokenUtils.token("厦门市"));
	}

}
