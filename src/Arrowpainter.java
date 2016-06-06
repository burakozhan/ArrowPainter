import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Arrowpainter {

	public static void main(String[] args) {
		
		
		doWorkedCountries("countries.csv");

	 
			
//		drawimagemostwanted ("Sandwich.png", "Südliche Sandwichinseln","unbew.", "Südatlantik", "VP8S", 12606, 4, 203-90, 203  );
		
		
	}
	
	public static void doWorkedCountries (String csvFile) {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
	 
		try {
	 
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
	 
				String[] country = line.split(cvsSplitBy);
				
				int distance = Integer.parseInt(country[6]);
				int heading = Integer.parseInt(country[7]);
				int contacts = Integer.parseInt(country[9]);
				
				drawimage (country[0]+".png",country[0],getTextPopulation(country[5]), country[1], country[2], distance , contacts, calcheadingpaintdir(heading), calcnorthpaintdir(heading) , heading  );
	 
				for(String e : country){
					System.out.print(e+" <---> ");
				}
				
				System.out.println();
	 
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getTextPopulation (String population){
		
		int pop = Integer.parseInt(population);
		
		if (pop < 1000 ) return Integer.toString(pop);
		
		if (pop < 1000000 ) return (Integer.toString(pop/1000))+" Tsd";
		
		return (Integer.toString(pop/1000000))+" Mio";
		
	}
	
	public static int calcheadingpaintdir (int heading){
		int deviation = 6; // Twist of platform from north
		if (heading >314) return heading+deviation;
		if (heading <43 ) return heading+deviation;
		if (heading <122 ) return heading-90+deviation;
		if (heading >215 ) return heading+90+deviation;
		return heading +180+deviation;
	}
	
	public static int calcnorthpaintdir (int heading){
		if (heading >314) return 0;
		if (heading <43 ) return 0;
		if (heading <122 ) return -90;
		if (heading >215 ) return 90;
		return 180;
	}

	
	public static void drawimagemostwanted (String filename, String country, String citizencount, String capitalcity, String callprefix, int distance,  int countcontacts, int headingpaintdirection, int heading ){
		BufferedImage image = new BufferedImage(1500, 350, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setBackground(Color.white);
		
        g2.setColor(Color.white);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        
		g2.setColor(Color.cyan);
		g2.drawLine(0, 0, 25, 0);
		g2.drawLine(0, 0, 0, 25);
		g2.drawLine(0, 349, 0, 325);
		g2.drawLine(0, 349, 25, 349);
		
		g2.drawLine(1499, 349, 1474, 349);
		g2.drawLine(1499, 0, 1474, 0);
		g2.drawLine(1499, 0, 1499, 25);
		g2.drawLine(1499, 349, 1499, 324);
		
		g2.setColor(Color.black);
		g2.drawRect(25, 25, 1500-50, 350-50);
		
		g2.setFont(new Font("Arial", Font.PLAIN, 60));
		int o = 93;
		int sep = 70;
		g2.drawString("Land:", 350, o);
		g2.drawString("Ort:", 350, sep*1+o);
		g2.drawString("Rufzeichen Präfix:", 350, sep*2+o);
		g2.drawString("Rang in \"DXCC Most Wanted\":", 350, sep*3+o);
		
		int l= 920;
		g2.drawString(country+" ("+citizencount+")", l-380, o);
		g2.drawString(capitalcity+" ("+distance+" km)", l-380, sep*1+o);
		g2.drawString(callprefix, l, sep*2+o);
		g2.drawString(Integer.toString(countcontacts), l+300, sep*3+o);
		
		g2.drawOval(25, 25, 300, 300);
		
		g2.translate(175, 175);
		g2.rotate(Math.toRadians(headingpaintdirection));
		
		g2.setColor(Color.black);
		int x = 00;
		int y = 00;
		int s = 15;
		
		g2.drawLine(x, y-120-s, x+60+s, y+100+s);
		g2.drawLine(x, y-120-s, x-60-s, y+100+s);
		g2.drawLine(x, y+50+s, x+60+s, y+100+s);
		g2.drawLine(x, y+50+s, x-60-s, y+100+s);
		
		g2.setFont(new Font("Arial", Font.PLAIN, 38));
		Rectangle r = g2.getFontMetrics().getStringBounds(""+heading+"°", g2).getBounds();
		g2.drawString(""+heading+"°", 0-(r.width/2), 0-(r.height/2)+60);
		
		g2.dispose();
		
		try {
		    File outputfile = new File(filename);
		    ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
		    System.err.println("ERR, something failed");
		}

	}
	
	public static void drawimage (String filename, String country, String citizencount, String capitalcity, String callprefix, int distance,  int countcontacts, int headingpaintdirection, int northdirection, int heading ){
		BufferedImage image = new BufferedImage(1500, 350, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setBackground(Color.white);
		
		g2.setColor(Color.white);
		g2.fillRect(0, 0, image.getWidth(), image.getHeight());
		
		g2.setColor(Color.cyan);
		g2.drawLine(0, 0, 25, 0);
		g2.drawLine(0, 0, 0, 25);
		g2.drawLine(0, 349, 0, 325);
		g2.drawLine(0, 349, 25, 349);
		
		g2.drawLine(1499, 349, 1474, 349);
		g2.drawLine(1499, 0, 1474, 0);
		g2.drawLine(1499, 0, 1499, 25);
		g2.drawLine(1499, 349, 1499, 324);
		
		g2.setColor(Color.black);
		g2.drawRect(25, 25, 1500-50, 350-50);
		
		g2.setFont(new Font("Arial", Font.PLAIN, 40));
		int o = 93;
		int sep = 70;
		
		int w1 = g2.getFontMetrics().getStringBounds("Land:", g2).getBounds().width;
		g2.drawString("Land:", 350, o);
		int w2 = g2.getFontMetrics().getStringBounds("Hauptstadt:", g2).getBounds().width;
		g2.drawString("Hauptstadt:", 350, sep*1+o);
		int w3 = g2.getFontMetrics().getStringBounds("Rufzeichen Präfix:", g2).getBounds().width;
		g2.drawString("Rufzeichen Präfix:", 350, sep*2+o);
		int w4 = g2.getFontMetrics().getStringBounds("Kontakte mit DK0TU in 2015:", g2).getBounds().width;
		g2.drawString("Kontakte mit DK0TU in 2015:", 350, sep*3+o);
		
//		int l= 920;
		int space = 50;
		
		//Rectangle r = g2.getFontMetrics().getStringBounds(country+" ("+citizencount+")", g2).getBounds();
		
		g2.setFont(new Font("Arial", Font.PLAIN, 60));
		g2.drawString(country,  350 + w1 + space , o);
		g2.drawString(capitalcity,  350 + w2 + space, sep*1+o);
		w2 += g2.getFontMetrics().getStringBounds(capitalcity, g2).getBounds().width;
		g2.drawString(callprefix, 350 + w3 + space , sep*2+o);
		g2.drawString(Integer.toString(countcontacts), 350 + w4 + space, sep*3+o);
		
		int ri = 1050;
		
		g2.setFont(new Font("Arial", Font.PLAIN, 40));
		int w5 = g2.getFontMetrics().getStringBounds("Einw.:", g2).getBounds().width;
		g2.drawString("Einw.:", ri, o);
		int w6 = g2.getFontMetrics().getStringBounds("Entf.:", g2).getBounds().width;
		g2.drawString("Entf.:", ri, sep*1+o);
		
		g2.setFont(new Font("Arial", Font.PLAIN, 60));
		g2.drawString(citizencount,  ri + w6 + space, o);
		g2.drawString(Integer.toString(distance)+" km",  ri + w6 + space, sep*1+o);
		
		g2.drawOval(25, 25, 300, 300);
		
		g2.translate(175, 175);
		g2.rotate(Math.toRadians(northdirection));
		g2.setFont(new Font("Arial", Font.PLAIN, 38));
		int xshift = g2.getFontMetrics().getStringBounds("N", g2).getBounds().width;
		g2.drawString("N", -(xshift/2), -120);
		
//		g2.translate(175, 175);
		g2.rotate(Math.toRadians(-northdirection));
		g2.rotate(Math.toRadians(headingpaintdirection));
		
		g2.setColor(Color.black);
		int x = 00;
		int y = 00;
		int s = 15;
		
		g2.drawLine(x, y-120-s, x+60+s, y+100+s);
		g2.drawLine(x, y-120-s, x-60-s, y+100+s);
		g2.drawLine(x, y+50+s, x+60+s, y+100+s);
		g2.drawLine(x, y+50+s, x-60-s, y+100+s);
		
		g2.setFont(new Font("Arial", Font.PLAIN, 38));
		Rectangle r = g2.getFontMetrics().getStringBounds(""+heading+"°", g2).getBounds();
		g2.drawString(""+heading+"°", 0-(r.width/2), 0-(r.height/2)+60);
		
		g2.dispose();
		
		try {
			File outputfile = new File(filename);
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
			System.err.println("ERR, something failed");
		}
		
	}
}
