package by.homesite.kpparser.net;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

/**
 * @author alex on 7/3/17.
 */
@Component
public class ProxyResolver implements IProxy {
   @Value("${useProxy}")
   private String useProxy;

   @Value("${proxyListFile}")
   private String proxyListFile;

   @Override
   public Proxy getProxy() {
      if (useProxy == null
            || !Boolean.parseBoolean(useProxy)
            || proxyListFile == null
            || !Files.exists(Paths.get(proxyListFile))) {
         return null;
      }

      String proxy = null;
      try {
         proxy = choose(new File(proxyListFile));
      } catch (FileNotFoundException e) {
         return null;
      }
      if (!StringUtils.isEmpty(proxy)) {
         String[] proxyItem = proxy.split(":");
         if (proxyItem.length < 2) {
            return null;
         }
         int port = Integer.parseInt(proxyItem[1].trim());
         Proxy proxyResult = new Proxy(
               Proxy.Type.HTTP,
               InetSocketAddress.createUnresolved(proxyItem[0], port)
         );
         return proxyResult;
      }
      return null;
   }

   private String choose(File f) throws FileNotFoundException
   {
      String result = null;
      Random rand = new Random();
      int n = 0;
      for(Scanner sc = new Scanner(f); sc.hasNext(); )
      {
         ++n;
         String line = sc.nextLine();
         if(rand.nextInt(n) == 0)
            result = line;
      }

      return result;
   }
}
