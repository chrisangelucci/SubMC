package submc.structures;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;

public class StructureLoader {

private static HashMap<String, Structure> loadedStructures = new HashMap<String, Structure>();
	
	private static final String BASE_PIECES_PATH = "structures\\basePieces";
	private static final String MACHINES_PATH = "structures\\machines";
	
	public static void loadStructures() {
		List<String> basePiecePaths = null;
		List<String> machinePaths = null;
		
		System.out.println("Looking for base pieces...");
		try (Stream<Path> walk = Files.walk(Paths.get(BASE_PIECES_PATH))) {
			basePiecePaths = walk.map(x -> x.toString())
					.filter(f -> f.endsWith(".json")).collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Error finding base pieces...");
			//TODO close the server?
			return;
		}
		
		if(basePiecePaths.size() == 0) {
			System.out.println("No base pieces found.");
		}else {
			System.out.println(basePiecePaths.size() + " base piece(s) found.");
			System.out.println("Loading base pieces...");
			basePiecePaths.forEach(path->{
				try {
					File f = new File(path);
					Structure s  = new Gson().fromJson(new FileReader(f), Structure.class);
					String name = f.getName().split("\\.")[0];
					loadBasePiece(name, s);
					System.out.println("Base Piece \"" + name + "\">");
					System.out.println("  Bounds: " + s.sizeX + ", " + s.sizeY + ", " + s.sizeZ);
				} catch (Exception e) {
					System.out.println("Error loading base piece.");
					e.printStackTrace();
				}
			});
		}
		
		System.out.println("Looking for machines...");
		try (Stream<Path> walk = Files.walk(Paths.get(MACHINES_PATH))) {
			machinePaths = walk.map(x -> x.toString())
					.filter(f -> f.endsWith(".json")).collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Error finding machines...");
			//TODO close the server?
			return;
		}
		
		if(machinePaths.size() == 0) {
			System.out.println("No machines found.");
		}else {
			System.out.println(machinePaths.size() + " machine(s) found.");
			System.out.println("Loading machines...");
			machinePaths.forEach(path->{
				try {
					File f = new File(path);
					Structure s  = new Gson().fromJson(new FileReader(f), Structure.class);
					String name = f.getName().split("\\.")[0];
					loadMachine(name, s);
					System.out.println("Machine \"" + name + "\">");
					System.out.println("  Bounds: " + s.sizeX + ", " + s.sizeY + ", " + s.sizeZ);
				} catch (Exception e) {
					System.out.println("Error loading machine.");
				}
			});
		}
	}
	
	public static Structure getStructure(String structureName) {
		return loadedStructures.get(structureName);
	}
	
	public static boolean isLoaded(String structureName) {
		return loadedStructures.containsKey(structureName);
	}
	
	private static void loadBasePiece(String name, Structure s) {
		loadedStructures.put(name, s);
		
	}
	
	private static void loadMachine(String name, Structure s) {
		loadedStructures.put(name, s);
	}
	
}
