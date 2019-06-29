import java.io.File;


public class Text_Filter extends javax.swing.filechooser.FileFilter{

	protected boolean isTextFile(String ext) {
		return (ext.equals("txt"));
	}

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = getExtension(f);
		if (extension.equals("txt")) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Supported Text Files";
	}

	protected static String getExtension(File f) {
		String s = f.getName();
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1) {
			return s.substring(i + 1).toLowerCase();
		}
		return "";
	}

}
