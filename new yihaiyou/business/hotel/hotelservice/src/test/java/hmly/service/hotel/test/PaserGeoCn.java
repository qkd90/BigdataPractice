//package hmly.service.hotel.test;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by caiys on 2016/5/20.
// */
//public class PaserGeoCn {
//
//    public static void main(String[] args) throws IOException {
//        BufferedReader in = new BufferedReader(new FileReader("E:\\ideaworkspace\\data\\business\\hotel\\hotelservice\\src\\test\\resources\\geo_cn.xml"));
//        BufferedWriter out = new BufferedWriter(new FileWriter("E:\\ideaworkspace\\data\\business\\hotel\\hotelservice\\src\\test\\resources\\geo_cn.out"));
//        String line = null;
//        while ((line = in.readLine()) != null) {
//            // <HotelGeo Country="中国" ProvinceName="北京" ProvinceId="0100" CityName="北京" CityCode="0101">
//            line = line.trim().replaceAll("\\t", "");
//            if (line.startsWith("<HotelGeo ")) {
//                StringBuilder sb = new StringBuilder("insert into elong_geo_cn(country,provinceName,provinceId,cityName,cityCode) values(");
//                Pattern p = Pattern.compile("Country=\"(\\S+)\"");
//                Matcher m = p.matcher(line);
//                sb.append("'");
//                if (m.find()) {
//                    String value = m.group(1);
//                    sb.append(value);
//                }
//                sb.append("',");
//                p = Pattern.compile("ProvinceName=\"(\\S+)\"");
//                m = p.matcher(line);
//                sb.append("'");
//                if (m.find()) {
//                    String value = m.group(1);
//                    sb.append(value);
//                }
//                sb.append("',");
//                p = Pattern.compile("ProvinceId=\"(\\S+)\"");
//                m = p.matcher(line);
//                sb.append("'");
//                if (m.find()) {
//                    String value = m.group(1);
//                    sb.append(value);
//                }
//                sb.append("',");
//                p = Pattern.compile("CityName=\"(\\S+)\"");
//                m = p.matcher(line);
//                sb.append("'");
//                if (m.find()) {
//                    String value = m.group(1);
//                    sb.append(value);
//                }
//                sb.append("',");
//                p = Pattern.compile("CityCode=\"(\\S+)\"");
//                m = p.matcher(line);
//                sb.append("'");
//                if (m.find()) {
//                    String value = m.group(1);
//                    sb.append(value);
//                }
//                sb.append("');");
//                out.write(sb.toString());
//                out.newLine();
//            }
//        }
//        out.flush();
//
//    }
//}
