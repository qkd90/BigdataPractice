package com.data.data.spark.base;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;

import java.io.Serializable;
import java.util.List;

public class KMeansSpark implements Serializable {

    public String kmeans(List<String> points) {
        System.setProperty("hadoop.home.dir", "F:\\迅雷下载\\hadoop-2.6.2");
        SparkConf conf = new SparkConf().setAppName("K-means Example");
        conf.setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        // Load and parse data
        String path = "E:\\IdeaProjects\\hmly\\data\\data\\planoptimize\\src\\main\\resources\\kmeans_data.txt";
        JavaRDD<String> data = sc.textFile(path);
        JavaRDD<Vector> parsedData = data.map(new Function<String, Vector>() {

            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public Vector call(String s) {
                String[] sarray = s.split(" ");
                double[] values = new double[sarray.length];
                for (int i = 0; i < sarray.length; i++) {
                    values[i] = Double.parseDouble(sarray[i]);
                }
                return Vectors.dense(values);
            }
        });
        parsedData.cache();

        // Cluster the data into two classes using KMeans
        int numClusters = 2;
        int numIterations = 200;
        final KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations);
        // Evaluate clustering by computing Within Set Sum of Squared Errors
        double WSSSE = clusters.computeCost(parsedData.rdd());
        System.out.println("Within Set Sum of Squared Errors = " + WSSSE);
        for (Vector vector : clusters.clusterCenters()) {
            System.out.println(vector.toString());
        }

        parsedData.map(new Function<Vector, Object>() {

            @Override
            public Object call(Vector vector) throws Exception {
                int cluster = clusters.predict(vector);
                System.out.println(String.format("%s Belong to Cluster %d", vector.toString(), cluster));
                return null;
            }
        }).collect();
        // Save and load model
//        clusters.save(sc.sc(), "myModelPath");
//        KMeansModel sameModel = KMeansModel.load(sc.sc(), "myModelPath");
//
//        System.out.println(sameModel);
        sc.close();
        return null;
    }

    public static void main(String[] args) {
        KMeansSpark kmean = new KMeansSpark();
        kmean.kmeans(null);
    }

}
