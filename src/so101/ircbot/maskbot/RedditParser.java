package so101.ircbot.maskbot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RedditParser 
{
	/**The url to fetch data from*/
	public String url;
	
	public String user = null;
	public String passwd = null;
	
	public List<String> TITLES = new ArrayList<String>();
	public List<String> LINKS = new ArrayList<String>();
	
	public RedditParser(String url)
	{
		this.url = url;
		this.fetchData();
	}
	
	public RedditParser(String url, String user, String password)
	{
		this.url = url;
		this.user = user;
		this.passwd = password;
		this.fetchData();
	}
	
	public void fetchData()
	{
		try
		{
			Document doc;
			if (this.user != null && this.passwd != null)
			{
				//Connection.Response loginForm = Jsoup.connect(url).method(Connection.Method.GET).execute();
				//doc = Jsoup.connect(url).data("user", this.user).data("passwd", this.passwd).data("api_type", "json").cookies(loginForm.cookies()).post();
				doc = Jsoup.connect("https://ssl.reddit.com/api/login").timeout(10000).data("user", this.user).data("passwd", this.passwd).method(Method.GET).referrer(url).maxBodySize(0).execute().parse();
			
			}
			else
			{
				doc = Jsoup.connect(url).timeout(10000).get();
			}
			
			Element elements = doc.getElementById("siteTable");
			for (Element e : elements.getElementsByTag("div"))
			{
				if (!e.getElementsByClass("rank").isEmpty() && e.getElementsByClass("rank").text() != "" && e.className().contains("thing id"))
				{
					IRCBot.log(e.toString(), Log.DEBUG);
					Element e5 = null;
					
					for (Element e6 : e.getElementsByTag("div"))
					{
						if (e6.className().contains("entry unvoted"))
						{
							e5 = e6;
						}
					}
					
					Element e1 = e5;
					//Element e1 = e.getElementsByClass("entry unvoted lcTagged").size() > 0 ? e.getElementsByClass("entry unvoted lcTagged").get(0) : null;
					if (e1 == null)
					{
						e1 = e.getElementsByClass("entry unvoted").get(0);
					}
					Element e2 = e1.getElementsByClass("title").get(0);
					Element title = e2.getElementsByTag("a").get(0);
					LINKS.add(title.attr("href"));
					TITLES.add(title.text());
					
					IRCBot.log(e1.toString(), Log.DEBUG);
					//IRCBot.log(e1.toString(), Log.DEBUG);
				}
			}
		}
		catch (IOException e) {IRCBot.log("An unexpected error occured while fetching data from " + this.url, Log.SEVERE);}	
		
	}
}
