
import org.apache.commons.io.FilenameUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Steganography_Controller {
	// Program Variables
	private Steganography_View view;
	private Steganography model;
	
	//Panel
	private JPanel decode_panel;
	private JPanel encode_panel;
	
	private JTextArea input;
	private JButton encodeButton, decodeButton, importOrigFile,importCropFile,importCoordFile,confirmButton;
	private JLabel image_input;
	// Menu Variables
	private JMenuItem encode;
	private JMenuItem decode;
	private JMenuItem exit;

	// action event classes
	private Encode enc;
	private Decode dec;
	private DecodeButton decButton;
	private ImportFileOrig impFilOrig;
	private ImportFileCrop impFileCrop;
	private ImportFileCoord impFileCoord;
	private ConfirmImport confImp;

	// decode variable
	private String stat_path = "";
	private String stat_name = "";

	//Text Field
	private final JTextField importOrigText, importCropText, importCoordText;

	public Steganography_Controller(Steganography_View aView, Steganography aModel) {
		
		// program variables
		this.view = aView;
		this.model = aModel;

		// views
		this.encode_panel = this.view.getTextPanel();
		this.decode_panel = this.view.getImagePanel();
		
		// data options
		this.input = this.view.getText();
		this.image_input = this.view.getImageInput();
		
		// buttons
		this.encodeButton = this.view.getEButton();
		this.decodeButton = this.view.getDButton();
		this.importOrigFile = this.view.getImportOrigFile();
		this.importCropFile = this.view.getImportCropFile();
		this.importCoordFile = this.view.getImportCoordFile();
		this.confirmButton = this.view.getConfirmButton();
		
		//Text Field 
		this.importOrigText = this.view.getImportOrigText();
		this.importCoordText = this.view.getImportCoordText();
		this.importCropText = this.view.getImportCropText();

		// menu
		this.encode = this.view.getEncode();
		this.decode = this.view.getDecode();
		this.exit = this.view.getExit();

		// assign action events
		this.enc = new Encode();
		this.encode.addActionListener(this.enc);

		this.dec = new Decode();
		this.decode.addActionListener(this.dec);

		this.exit.addActionListener(new Exit());

		this.decButton = new DecodeButton();
		this.decodeButton.addActionListener(this.decButton);

		this.impFilOrig = new ImportFileOrig();
		this.importOrigFile.addActionListener(this.impFilOrig);

		this.impFileCoord = new ImportFileCoord();
		this.importCoordFile.addActionListener(this.impFileCoord);

		this.impFileCrop = new ImportFileCrop();
		this.importCropFile.addActionListener(this.impFileCrop);

		this.confImp = new ConfirmImport();
		this.confirmButton.addActionListener(this.confImp);
		encode_view();
	}

	private void encode_view() {
		update();
		this.view.setContentPane(this.encode_panel);
		this.view.setVisible(true);
	}

	private void decode_view() {
		update();
		this.view.setContentPane(this.decode_panel);
		this.view.setVisible(true);
	}

	private class Encode implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			importCropText.setText("");
			importCoordText.setText("");
			importOrigText.setText("");
			encode_view(); 
		}
	}

	private class ImportFileCrop implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(Steganography_Controller.this.view);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File directory = chooser.getSelectedFile();
				importCropText.setText(directory.toString());
			}
		}
	}
	private class ImportFileCoord implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Text_Filter());
			int returnVal = chooser.showOpenDialog(Steganography_Controller.this.view);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File directory = chooser.getSelectedFile();
				System.out.println( "directory: "+ directory);
				importCoordText.setText(directory.toString());

			}

		}

	}
	private class ConfirmImport implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				String nomeCrop = importCropText.getText();
				String nomeCoord = importCoordText.getText();
				String nomeOrig = importOrigText.getText();
				String ext= nomeOrig.substring(nomeOrig.lastIndexOf('.')+1,nomeOrig.length());
				nomeOrig = FilenameUtils.getName(nomeOrig);
				String pathOrig = importOrigText.getText();
				pathOrig = pathOrig.substring(0, pathOrig.length() - nomeOrig.length() - 1);
				nomeOrig= nomeOrig.substring(0, nomeOrig.lastIndexOf('.'));
				String stegan = JOptionPane.showInputDialog(Steganography_Controller.this.view, "Enter output file name:", "File name",
						JOptionPane.PLAIN_MESSAGE);
				if (Steganography_Controller.this.model.encode(pathOrig, nomeOrig, ext, stegan, nomeCrop, nomeCoord)) {
					JOptionPane.showMessageDialog(Steganography_Controller.this.view, "The Image was encoded Successfully!", "Success!",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(Steganography_Controller.this.view, "The Image could not be encoded!", "Error!",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception except) {
				JOptionPane.showMessageDialog(Steganography_Controller.this.view, "The File cannot be opened!", "Error!",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private class ImportFileOrig implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(Steganography_Controller.this.view);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File directory = chooser.getSelectedFile();
				importOrigText.setText(directory.toString());
			}
		}
	}

	
	private class Decode implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			decode_view();
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(Steganography_Controller.this.view);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File directory = chooser.getSelectedFile();
				try {
					String image = directory.getPath();
					Steganography_Controller.this.stat_name = directory.getName();
					Steganography_Controller.this.stat_path = directory.getPath();
					Steganography_Controller.this.stat_path = Steganography_Controller.this.stat_path.substring(0,
							Steganography_Controller.this.stat_path.length() - Steganography_Controller.this.stat_name.length() - 1);
					Steganography_Controller.this.stat_name = Steganography_Controller.this.stat_name.substring(0,
							Steganography_Controller.this.stat_name.length() - 4);
					Steganography_Controller.this.image_input.setIcon(new ImageIcon(ImageIO.read(new File(image))));
				} catch (Exception except) {
					JOptionPane.showMessageDialog(Steganography_Controller.this.view, "The File cannot be opened!", "Error!",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	private class Exit implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0); 
		}
	}

	private class DecodeButton implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			String message = Steganography_Controller.this.model.decode(Steganography_Controller.this.stat_path,
					Steganography_Controller.this.stat_name);
			System.out.println(Steganography_Controller.this.stat_path + ", " + Steganography_Controller.this.stat_name);
			if (message != "") {
				encode_view();
				JOptionPane.showMessageDialog(Steganography_Controller.this.view, "The Image was decoded Successfully!", "Success!",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(Steganography_Controller.this.view, "The Image could not be decoded!", "Error!",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}


	public void update() {
		this.image_input.setIcon(null); 
		this.stat_path = ""; 
		this.stat_name = "";
	}

	public static void main(String args[]) {
		new Steganography_Controller(new Steganography_View("Steganography"), new Steganography());
	}

}