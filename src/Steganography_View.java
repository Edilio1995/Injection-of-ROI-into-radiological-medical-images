import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;


public class Steganography_View extends JFrame {
    private static final long serialVersionUID = -1703553958323231590L;
    private static int WIDTH = 500;
    private static int HEIGHT = 400;

    private JTextArea input;
    private JButton encodeButton, decodeButton;
    private JLabel image_input;

    private JMenuItem encode;
    private JMenuItem decode;
    private JMenuItem exit;
    
    private JButton importOrigFile,importCropFile,importCoordFile,confirmButton;
    private JTextField importOrigText, importCropText, importCoordText;
    
    public JTextField getImportOrigText() {
		return importOrigText;
	}

	public void setImportOrigText(JTextField importOrigText) {
		this.importOrigText = importOrigText;
	}

	public JTextField getImportCropText() {
		return importCropText;
	}

	public void setImportCropText(JTextField importCropText) {
		this.importCropText = importCropText;
	}

	public JTextField getImportCoordText() {
		return importCoordText;
	}

	public void setImportCoordText(JTextField importCoordText) {
		this.importCoordText = importCoordText;
	}

    public Steganography_View(String name) {
        
        super(name);

        JMenuBar menu = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic('F');
        this.encode = new JMenuItem("Encode");
        this.encode.setMnemonic('E');
        file.add(this.encode);
        this.decode = new JMenuItem("Decode");
        this.decode.setMnemonic('D');
        file.add(this.decode);
        file.addSeparator();
        this.exit = new JMenuItem("Exit");
        this.exit.setMnemonic('x');
        file.add(this.exit);

        menu.add(file);
        setJMenuBar(menu);

        setResizable(true); 
        setBackground(Color.lightGray); 
        setLocation(100, 100); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT); 
        setVisible(true); 
    }

    public JMenuItem getEncode() {
        return this.encode;
    }

    public JMenuItem getDecode() {
        return this.decode;
    }

    public JMenuItem getExit() {
        return this.exit;
    }

    public JTextArea getText() {
        return this.input;
    }

    public JLabel getImageInput() {
        return this.image_input;
    }

    public JPanel getTextPanel() {
        return new Text_Panel();
    }

    public JPanel getImagePanel() {
        return new Image_Panel();
    }
    
    public JButton getEButton() {
        return this.encodeButton;
    }

    public JButton getDButton() {
        return this.decodeButton;
    }
    
    private class Text_Panel extends JPanel {
        private static final long serialVersionUID = -1503714729990491598L;

        public Text_Panel() {
        	setLayout(new GridBagLayout());
        	GridBagConstraints c = new GridBagConstraints();
        	importOrigFile = new JButton("Import");
        	importCropFile = new JButton("Import");
        	importCoordFile = new JButton("Import");
        	confirmButton = new JButton("Confirm");
        	
    		JLabel importOrig = new JLabel("Inserisci immagine originale");
    		JLabel importCrop = new JLabel("Inserisci crop immagine");
    		JLabel importCoord = new JLabel("Inserisci file cordinate");

    		c.insets = new Insets(2,10,0,10); 
    		c.fill = GridBagConstraints.HORIZONTAL;
    		c.weightx = 1;
    		c.gridx = 0;
    		c.gridy = 0;
    		add(importOrig, c);
    		
    		importOrigText = new JTextField();
    		c.fill = GridBagConstraints.HORIZONTAL;
    		c.weightx = 0.7;
    		c.gridx = 0;
    		c.gridy = 1;
    		add(importOrigText, c);
    		
    		c.fill = GridBagConstraints.HORIZONTAL;
    		c.weightx = 0.1;
    		c.weighty = 0.1;
    		c.gridx = 1;
    		c.gridy = 1;
    		add(importOrigFile, c);
    		
    		c.fill = GridBagConstraints.HORIZONTAL;
    		c.weightx = 1;
    		c.gridx = 0;
    		c.gridy = 2;
    		add(importCrop, c);
    		
    		importCropText = new JTextField();
    		c.fill = GridBagConstraints.HORIZONTAL;
    		c.weightx = 0.7;
    		c.gridx = 0;
    		c.gridy = 3;
    		add(importCropText, c);

    		c.fill = GridBagConstraints.HORIZONTAL;
    		c.weightx = 0.1;
    		c.weighty = 0.1;
    		c.gridx = 1;
    		c.gridy = 3;
    		add(importCropFile, c);
    		
    		c.fill = GridBagConstraints.HORIZONTAL;
    		c.weightx = 1;
    		c.gridx = 0;
    		c.gridy = 4;
    		add(importCoord, c);

    		importCoordText = new JTextField();
    		c.fill = GridBagConstraints.HORIZONTAL;
    		c.weightx = 0.7;
    		c.gridx = 0;
    		c.gridy = 5;
    		add(importCoordText, c);

    		c.fill = GridBagConstraints.HORIZONTAL;
    		c.weightx = 0.1;
    		c.weighty = 0.1;
    		c.gridx = 1;
    		c.gridy = 5;
    		add(importCoordFile, c);
    		
    		c.insets = new Insets(0,140,0,140); 
    		c.fill = GridBagConstraints.HORIZONTAL;
    		c.ipady = 0;       
    		
    		c.gridx = 0;       
    		c.gridwidth = 2;   
    		c.weightx = 0.4;
    		c.gridy = 6;       
    		add(confirmButton, c);
            setBackground(Color.lightGray);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }
    }

    public JButton getImportOrigFile() {
		return importOrigFile;
	}

	public void setImportOrigFile(JButton importOrigFile) {
		this.importOrigFile = importOrigFile;
	}

	public JButton getImportCropFile() {
		return importCropFile;
	}

	public void setImportCropFile(JButton importCropFile) {
		this.importCropFile = importCropFile;
	}

	public JButton getImportCoordFile() {
		return importCoordFile;
	}

	public void setImportCoordFile(JButton importCoordFile) {
		this.importCoordFile = importCoordFile;
	}

	public JButton getConfirmButton() {
		return confirmButton;
	}

	public void setConfirmButton(JButton confirmButton) {
		this.confirmButton = confirmButton;
	}

    private class Image_Panel extends JPanel {
        private static final long serialVersionUID = 6962328466941120160L;

        public Image_Panel() {
            GridBagLayout layout = new GridBagLayout();
            GridBagConstraints layoutConstraints = new GridBagConstraints();
            setLayout(layout);
            Steganography_View.this.image_input = new JLabel();
            layoutConstraints.gridx = 0;
            layoutConstraints.gridy = 0;
            layoutConstraints.gridwidth = 1;
            layoutConstraints.gridheight = 1;
            layoutConstraints.fill = GridBagConstraints.BOTH;
            layoutConstraints.insets = new Insets(0, 0, 0, 0);
            layoutConstraints.anchor = GridBagConstraints.CENTER;
            layoutConstraints.weightx = 1.0;
            layoutConstraints.weighty = 50.0;
            JScrollPane scroll2 = new JScrollPane(Steganography_View.this.image_input, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            layout.setConstraints(scroll2, layoutConstraints);
            scroll2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            Steganography_View.this.image_input.setHorizontalAlignment(SwingConstants.CENTER);
            add(scroll2);
            Steganography_View.this.decodeButton = new JButton("Decode Now");
            layoutConstraints.gridx = 0;
            layoutConstraints.gridy = 1;
            layoutConstraints.gridwidth = 1;
            layoutConstraints.gridheight = 1;
            layoutConstraints.fill = GridBagConstraints.BOTH;
            layoutConstraints.insets = new Insets(0, -5, -5, -5);
            layoutConstraints.anchor = GridBagConstraints.CENTER;
            layoutConstraints.weightx = 1.0;
            layoutConstraints.weighty = 1.0;
            layout.setConstraints(Steganography_View.this.decodeButton, layoutConstraints);
            add(Steganography_View.this.decodeButton);
            setBackground(Color.lightGray);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }
    }

    public static void main(String args[]) {
        new Steganography_View("Steganography");
    }
}