package spider;

import com.data.spider.util.HttpClientUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Sane on 15/9/10.
 */
public class baiduKey {
    public static void main(String[] args) {
//        String[] keys = {"hk50lSu1bD6Stj5KY8eKGwvo","Qo0NgQcU7PKnngOl2y0EGr0P","zZ9AKfqAthPDGzRp4SRSueKu","T7rOGOk6KUj6jEprAjTcM9DL","pfhsvjvB3ipP2CWa1QL3nzIf","DWWmjCyZjckAhTGmsbbfMQ4p","yeIkkwIOLqs3ZtMQE0kRu9ce","fN0Yz5l0jOsGtSXjqwlBNeQG","6WSGGMVw5HAKjZeZTod5XSxW","uXSX8FZBBXL88EjjKU72ysBS","Q2qFnkDFG3x2RDzzjKXWrqky","4HKdvSHNrji5Tsx0vOqA25b8","QbMxChxq9qQ28XwUuqNeZ7Gw","9n9n2wtY5Tnubi8k15DjblB9","2VlkjQBaPQHL6kTwzVfNXajy","oikaIGAaUCFnUPDHuKlDULD0","2NQZH5b7jUhDzEdssyvyMSqO","8n2yayF4sdhGryiTxT63abwY","nluKGNokqUjrkEg6LSKFWP2A","hrRCWhfNp8qkKDMSA5KAoMG0","2Z1Oel8f8z657k7IVHGMYKUf","PbItMqlo7em3fRqAVbN3BnZn","aQsK8HXgFHo0kwYyGzsXhRt4","KqCNG5yygTLsCwObEUzVEnbq","0SAXKvIGyCcmxwTtFn2i50EA","LZICDzg8kvPaBDMbNkbHpDvt","0dqIZrNwLLjtSqUTWBBLd7De","UxnHh7HICVzVHubthGyNqq5R","UZIcqtElt03030R7HzcbymTB","oPNSNjvNhzOsw5vEajppE6xO","BH4qAQoExlTfuodtVIXaVWzw","bk0m6hdV5YDhuGlnFLAjrHWM","GpVNGaGj0yzdEoTTYEWF5oPI","tdsy5tZRglZoDSKw8vpfZHus","l9sGQOV65VkmAwmXMOqiWhnp","uQy1pyGrYkuY816lA0a8EpQ5","QB4BySGiIGYeR0z0T6i3Zf1p","LGvfTRBXfxCGKSrvZDrSlGo4","ZxfzGYRq0yEfVGXe2FCXjSpZ","c7KTeXP8PDGG3ClrujCnFFYh","ZUn91NBUvGPN9c52dLPiaa4O","hNuExztk90s91DkuTj3lEQaM","8uA0CTWrHAGu1134Tl2GDHGN","kVS57kIcqmENz4vSm4UNsnX3","GmpIoYgMNIRHKwmmvUBkSyRG","O4B9YoRDMxlhovLt4IOyukvq","pz3zovdVnzSwiSfdzmzBmBS6","2ns7OBFKGxflnpVnlb8Et6re","ziyUasXwpNbNEd1zbq5EL1fu","CIGmrRYQTI9wMohQcBsFQu0f","8YicATNKAGuu58aE67SL6GTA"};
//        String[] keys = {"7A2InQp00VI5V68XXdRrGYA4","N1bGkyxF4Y0smZVdln139oqx","nLQRUHYkZWzBwTLhDiRFs1BB","P8fCVI1RYZf51Gg72Zdrer7P","OalYKLgMjILUKmP9IRfzileP","voo1xE58VxowNzFSAGYWjOaG","gvopQQfZCuDLBRASetpyrFNu","D7wMLRLG0caKCT3sbMkZTTXE","c3h8jFNZ814j0KTswGhOKjj0","pbNzpqkyiBGZW3bwSn0AhyrT","ZkEVZAVC3ydhsZvNYWiiyE6X","tdZIG8SIFs3tw3DRjxke45xr","GBX5EMwGalSWXXfgEGW55dic","h4eylWK53UDk8ZkCyIGkGYHv","m9zLPkNMvIDkL2ClvCBrhcGu","R5VG7ErnflGWg43IUhAgZlKM","ddktRmfZBXQ14MUCYEwGcL1i","2LFQ889EhKOon6XGjWb0GiNK","Ptfb8OVTPA5In5zSNjXZdWEu","PWEYDtBGNaeYHvARozCY9A0v","ZBehyIvLhi5COTG46FlxdtFK","mzbA9LqhGB7RxasYD2TEAcGR","Za54Bn7vlnoQr0XQs3WFxNSZ","oLIy9jRtwyi7XjpXjx2O8K0f","9FzD2jR1EBhoCfOhplG3sn5g","SjKkVvrsyQ6LakGGe63Y5ooO","cysWTnmo5waO9euYpCiN9DiD","XYXsSHY8UCEmq0uoekjMbLgv","Vf1noezZRx6RUubryqq85tDI","ZFRskFKMuHFt63l6tOkGU3Tb","kCdx8Uea5hYO0XfCC33pTBXN","p35svdwotO5MDwANOlvRuYnx","w6hGFzxfxABSkcgxsceEv1Bg","KPvg83PGuuW1AXuI2vIZYnfZ","l5zGjhxuIB8xsISbG5nGHwQg","3zpB3DWqxdw6RELoVDISObHZ","NIG6TwxoSqB7iiMrqvSV5A1F","cVyhHXp5384EYBrgIDpwB2ZG","vpPrcb6sDYiue76KMy9CulhT","Gx2uWmdbzQaHvQDpbhUci94r","dGTbHI4agXO6zU5Pl6wehNbF","ZeDe3qfxLOYDEpIEZNYUs08F","1gRGCYzXpeFFi2oAcGxOe95B","yY17IkxctKQfLFPvpensGD5D","gyCdofIP4cGvM1AmeYl1mNtQ","5dW91M8VL739mUBFPHpG657P","poPXU3q9Kaz9yiTjZFNBGXja","uPcSDh03Gt5gq5US20mRNTQw","uYQxbjbMtYmtmWnw2uBaojMU","jGDN3qSeZv9gttTskerWjHDY"};
//        String[] keys = {"7A2InQp00VI5V68XXdRrGYA4","Gzt5MrqG4xfOYgL3M94BCbIx","K75Q6AjjMPgBLesTRZVHYeLT","xdiHKQ3g05KqLpyyjSQfwyLr"};
//        String[] keys = {"l8BsCcyjP2B2qSvfaj9la7TA","PXhzqOZRCWLy6dzlwQuF3gpV"};
        String[] keys = {"PXhzqOZRCWLy6dzlwQuF3gpV","7A2InQp00VI5V68XXdRrGYA4","Gzt5MrqG4xfOYgL3M94BCbIx","K75Q6AjjMPgBLesTRZVHYeLT","xdiHKQ3g05KqLpyyjSQfwyLr","X16T1kkjj8aRFbKeeESWYkyP","YwGhG2HXnkDvpZnHaB2FXhNN","QSDtFvyVimDFlxMdFbfUemCo","0YtU0vASre7BR1LGxAHfUy1P","hsg4CinfOhEf095lrARdizMC"};
        String url ="http://api.map.baidu.com/direction/v1?mode=driving&origin=39.827910523623444,115.94442436310865&destination=39.89810486797763,116.40481089075243&origin_region=北京&destination_region=北京&output=json&ak=";
        for (int i = 0; i < keys.length; i++) {
            System.out.println(keys[i]);
            HttpClient httpClient = HttpClientUtils.getHttpClient();
            HttpGet get = new HttpGet(url+keys[i]);
            HttpResponse response = null;
            try {
                response = httpClient.execute(get);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                String baiduStr = EntityUtils.toString(response.getEntity(), "utf-8");
                System.err.println(baiduStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
