package tools;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface de normalisation des mots
 * @author xtannier
 *
 */
public interface Normalizer {
	public ArrayList<String> normalize(String fileName) throws IOException;
}
