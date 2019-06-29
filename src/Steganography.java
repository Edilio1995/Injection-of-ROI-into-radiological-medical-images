import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Scanner;
import java.util.Base64.Decoder;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Steganography {
	String globalmessage;

	@SuppressWarnings ("javadoc")
	public Steganography() {
	}

	int compressedDataLength;
	byte[] output;


	public boolean encode(String path, String original, String ext1, String stegan, String pathCrop, String pathCoord) {
			String file_name = image_path(path, original, ext1);
		BufferedImage image_orig = getImage(file_name);
		int row = image_orig.getWidth();
		int start = 0,end = 0, dim=0;
		Scanner sc=null;
		File file = new File(pathCoord);
		try {
			sc = new Scanner(file);
			int maxX= sc.nextInt();
			int minX= sc.nextInt();
			sc.nextLine();
			sc.nextLine();
			int maxY= sc.nextInt();
			int minY= sc.nextInt();
			start = ((minX*row)+maxX)*3;
			end =((minY* row)+maxY)*3;
			dim= maxY-maxX;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			sc.close();
		}
		BufferedImage image = user_space(image_orig);
		String gg = encoder(pathCrop);
		int length = gg.length();
		System.out.println("input dimensione: " + length);
		byte[] input = null;
		byte[] inputF = null;
		try {
			input = gg.getBytes("UTF-8");
			this.output = new byte[length];
			Deflater compresser = new Deflater();
			compresser.setInput(input);
			compresser.finish();
			this.compressedDataLength = compresser.deflate(this.output);
			inputF = new byte[this.compressedDataLength];
			Deflater compresser2 = new Deflater();
			compresser2.setInput(input);
			compresser2.finish();
			this.compressedDataLength = compresser2.deflate(inputF);
			System.out.println("compressedDataLength: "+compressedDataLength);
			image = add_text(image, inputF, start, end, row , dim);
			if(image!=null){
				return (setImage(image, new File(image_path(path, stegan, "png")), "png"));
			}
		} catch (UnsupportedEncodingException exc) {
			exc.printStackTrace();
		}
		return false;
	}

	public String decode(String path, String name) {
		byte[] decode, imageByte;
		ByteArrayInputStream bis ;
		BufferedImage image = null,  image2 = null;
		try {
			image = user_space(getImage(image_path(path, name, "png")));
			decode = decode_text(get_byte_data(image));
			
			Deflater compresser = new Deflater();
			this.compressedDataLength = compresser.deflate(decode);
			
			Inflater decompresser = new Inflater();
			decompresser.setInput(decode, 0, new String(decode).length());
			byte[] result = new byte[100000000];
			int resultLength = decompresser.inflate(result);
			decompresser.end();
			String outputString = new String(result, 0, resultLength, "UTF-8");
			
			Decoder decoder= Base64.getDecoder();
			imageByte = decoder.decode(outputString);
			bis = new ByteArrayInputStream(imageByte);
			image2 = ImageIO.read(bis);
			File outputfile =new File(path+"/CropImageOut.png");
			ImageIO.write(image2, "png", outputfile);
			
			bis.close();
			System.out.println("Dimensione decode dopo decomp: " +outputString.length());
			return outputString;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "There is no hidden message in this image!", "Error", JOptionPane.ERROR_MESSAGE);
			return "";
		}
	}

	private String image_path(String path, String name, String ext) {
		return path + "/" + name+ "."+ ext;
	}

	private BufferedImage getImage(String f) {
		BufferedImage image = null;
		File file = new File(f);
		try {
			image = ImageIO.read(file);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Image could not be read!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}

	private boolean setImage(BufferedImage image, File file, String ext) {
		try {
			file.delete();
			ImageIO.write(image, ext, file);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "File could not be saved!", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	private BufferedImage add_text(BufferedImage image, byte msg[], int start, int end, int row, int dim) {
		byte img[] = get_byte_data(image);
		byte len[] = bit_conversion(msg.length);
		try {
			encode_text(img, len, 0, start, end, row, dim); // 0 first positiong
			encode_text(img, msg, 32, start, end, row, dim); // 4 bytes of space for length: 4bytes*8bit = 32 bits
			return image;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Target File cannot hold message!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	private BufferedImage user_space(BufferedImage image) {
		BufferedImage new_img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics = new_img.createGraphics();
		graphics.drawRenderedImage(image, null);
		graphics.dispose(); 
		return new_img;
	}

	private byte[] get_byte_data(BufferedImage image) {
		WritableRaster raster = image.getRaster();
		DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
		return buffer.getData();
	}

	private byte[] bit_conversion(int i) {
		byte byte3 = (byte) ((i & 0xFF000000) >>> 24);
		byte byte2 = (byte) ((i & 0x00FF0000) >>> 16); 
		byte byte1 = (byte) ((i & 0x0000FF00) >>> 8); 
		byte byte0 = (byte) ((i & 0x000000FF));
		return (new byte[] { byte3, byte2, byte1, byte0 });
	}

	private byte[] encode_text(byte[] image, byte[] addition, int offset, int start, int end, int row, int dim) {
		if (addition.length + offset > image.length) {
			throw new IllegalArgumentException("File not long enough!");
		}
		for (int i = 0; i < addition.length; ++i) {
			int add = addition[i];
			for (int bit = 7; bit >= 0; --bit, ++offset)
			{
				int b = (add >>> bit) & 1;
				if(!(start == offset)) {
					image[offset] = (byte) ((image[offset] & 0xFE) | b);	
				}
				else{
					start= start+ row*3;
					offset= offset+ dim*3;
					i--;
				}
				if(offset == end) {
					start = 100000000;
				}
			}   
		}
		return image;
	}

	private byte[] decode_text(byte[] image) {
		int length = 0;
		int offset = 32;
		for (int i = 0; i < 32; ++i)
		{
			length = (length << 1) | (image[i] & 1);
		}
		byte[] result = new byte[length];
		for (int b = 0; b < result.length; ++b) {
			for (int i = 0; i < 8; ++i, ++offset) {
				result[b] = (byte) ((result[b] << 1) | (image[offset] & 1));
			}
		}
		return result;
	}

	@SuppressWarnings ("javadoc")
	public static String encoder(String imagePath) {
		String base64Image = "";
		File file = new File(imagePath);
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);
			base64Image = Base64.getEncoder().encodeToString(imageData);
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
		return base64Image;
	}
}