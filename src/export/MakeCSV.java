package export;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import display.Audit.MainActivity;
import fsTablesClasses.Building;
import fsTablesClasses.EnvelopeInformation;
import fsTablesClasses.EquipmentInformation;
import fsTablesClasses.LightingInformation;
import fsTablesClasses.Measure;
import fsTablesClasses.MeasureSummary;
import fsTablesClasses.SiteMedia;
import fsTablesClasses.UtilityMeterInformation;
import fsTablesClasses.WaterInformation;

/**
 * This class grabs data from CSV file
 * 
 * @author Steven Wancewicz, S01938415
 * @version 2013-9-6, CSC-241 Assignment 4, Exercise 18.9
 * 
 */
public class MakeCSV {
	
	File root;
	File dir;
	Context mContext;
	int numBuildings;
	@SuppressWarnings("unused")
	private static final String CLASS_NAME = "MakeCSV";
	
	public boolean sendCSV(Context context) {
		boolean sent = false;
		numBuildings = MainActivity.db.getActiveFacilityBuildingCount();
		File eFile;
		File lFile;
		File enFile;
		File wFile;
		File bFile;
		File pFile;
		File mFile;
        File uFile;
        File eFile2;
        File lFile2;
        File enFile2;
        File wFile2;
        File bFile2;
        File pFile2;
        File mFile2;
        File uFile2;
		int buildingIndex;

		mContext = context;

        root   = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        dir    =   new File (root.getAbsolutePath() + "/EquipmentData");
		//For emulator testing (will crash if no email is setup, but will create files)
		//dir = new File("data/data/com.example.fstables2/cache");
	    //Log.v("MakeCSV", "" + dir.mkdirs());
	    buildingIndex = MainActivity.db.getActiveBuildingIndex();
	    
	    eFile = new File(context.getFilesDir(), "Equipment.csv");
		eFile.setReadable(true, false);
	    lFile = new File(context.getFilesDir(), "Lighting.csv");
		lFile.setReadable(true, false);
		enFile = new File(context.getFilesDir(), "Envelope.csv");
		enFile.setReadable(true,false);
		wFile = new File(context.getFilesDir(), "Water.csv");
		wFile.setReadable(true,false);
	    bFile = new File(context.getFilesDir(), "Building.csv");
		bFile.setReadable(true,false);
	    pFile = new File(context.getFilesDir(), "Media.csv");
		pFile.setReadable(true,false);
	    mFile = new File(context.getFilesDir(), "Measures.csv");
		mFile.setReadable(true,false);
        uFile = new File(context.getFilesDir(), "UtilityMeter.csv");
        uFile.setReadable(true,false);

        eFile2 = new File(dir, "Equipment.csv");
        lFile2 = new File(dir, "Lighting.csv");
        enFile2 = new File(dir, "Envelope.csv");
        wFile2 = new File(dir, "Water.csv");
        bFile2 = new File(dir, "Building.csv");
        pFile2 = new File(dir, "Media.csv");
        mFile2 = new File(dir, "Measures.csv");
        uFile2 = new File(dir, "UtilityMeter.csv");
        /*
        */

        ArrayList<Uri> files = new ArrayList<Uri>();

	    if (MakeEquipmentCSV(eFile) && MakeLightingCSV(lFile) &&MakeBuildingCSV(bFile) &&MakeMediaCSV(pFile)  &&MakeMeasureCSV(mFile) &&MakeEnvelopeCSV(enFile) &&MakeWaterCSV(wFile)
        &&MakeMeterCSV(uFile)) {
            files.add(Uri.fromFile(eFile));
            files.add(Uri.fromFile(lFile));
            files.add(Uri.fromFile(bFile));
            files.add(Uri.fromFile(pFile));
            files.add(Uri.fromFile(mFile));
            files.add(Uri.fromFile(wFile));
            files.add(Uri.fromFile(enFile));
        }

        if (MakeEquipmentCSV(eFile2) && MakeLightingCSV(lFile2) && MakeBuildingCSV(bFile2) &&MakeMediaCSV(pFile2) &&MakeMeasureCSV(mFile2) && MakeEnvelopeCSV(enFile2) &&MakeWaterCSV(wFile2)
                &&MakeMeterCSV(uFile2)) {

            files.add(Uri.fromFile(eFile2));
            files.add(Uri.fromFile(lFile2));
            files.add(Uri.fromFile(bFile2));
            files.add(Uri.fromFile(pFile2));
            files.add(Uri.fromFile(mFile2));
            files.add(Uri.fromFile(wFile2));
            files.add(Uri.fromFile(enFile2));

        }

        Intent sendIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Facility Equipment, Lighting, and Photo Info");
		sendIntent.setType("text/html");
		sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
		context.startActivity(sendIntent);
		sent = true;

	    MainActivity.db.setActiveBuildingID(buildingIndex);
	    return sent;
	}
	
	private boolean MakeEquipmentCSV(File files) {
		boolean error = false;
		List<EquipmentInformation> equipment = new ArrayList<EquipmentInformation>();
		EquipmentInformation ei = new EquipmentInformation();
		equipment.add(ei);
		BufferedWriter out = null;
		String buildingName;
		
		try {
			FileWriter fileWriter = new FileWriter(files);
			out = new BufferedWriter(fileWriter);
			out.write(EquipmentInformation.csvHeaders);
			FileOutputStream outputStream;
			Log.v("Issue", mContext.getApplicationContext().toString());
			//outputStream = mContext.getApplicationContext().openFileOutput("Equipment.csv", Context.MODE_WORLD_READABLE);
			//outputStream.write(EquipmentInformation.csvHeaders.getBytes());

			for(int i = 0; i<numBuildings-1; i++) {
				equipment.clear();
				////Log.v("MakeCSV/MakeEquipmentCSV", "Equipment i: " + i);
				MainActivity.db.setActiveBuildingID(i);
				equipment = MainActivity.db.getAllEquipmentInformation();
				buildingName = MainActivity.db.getActiveBuilding().getBuildingFileName();
				//Log.v("MakeCSV/MakeEquipmentCSV", "# of Equipment: " + equipment.size() );
				for(EquipmentInformation e:equipment) {
					out.write(replaceNull(e.csvExport(buildingName)));
				}
			}
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			error = true;
		}

		return error;
	}
	
	private boolean MakeLightingCSV(File files) {
		boolean error = false;
		List<LightingInformation> light;
		BufferedWriter out = null;
		String buildingName;
		
		try {
			FileWriter fileWriter = new FileWriter(files);
			out = new BufferedWriter(fileWriter);
			out.write(LightingInformation.headers);

			for(int i = 0; i<numBuildings-1; i++) {
			    MainActivity.db.setActiveBuildingID(i);
				light = MainActivity.db.getAllLightingInformation();
				buildingName = MainActivity.db.getActiveBuilding().getBuildingFileName();
				for(LightingInformation l:light) {
					out.write(replaceNull(l.csvExport(buildingName)));
				}
			}
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				out.close();
				error = true;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		return error;
	}

	private boolean MakeEnvelopeCSV(File files) {
		boolean error = false;
		List<EnvelopeInformation> envelope;
		BufferedWriter out = null;
		String buildingName;

		try {
			FileWriter fileWriter = new FileWriter(files);
			out = new BufferedWriter(fileWriter);

            out.write(EnvelopeInformation.csvExportHeaders());

            for(int i = 0; i<numBuildings-1; i++) {
				MainActivity.db.setActiveBuildingID(i);
				envelope = MainActivity.db.getAllEnvelopeInformation();
				buildingName = MainActivity.db.getActiveBuilding().getBuildingFileName();
				for(EnvelopeInformation e:envelope) {
					out.write(replaceNull(e.csvExport(buildingName)));
				}
			}
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				out.close();
				error = true;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

		return error;

	}

	private boolean MakeWaterCSV(File files) {
		boolean error = false;
		List<WaterInformation> water;
		BufferedWriter out = null;
		String buildingName;

		try {
			FileWriter fileWriter = new FileWriter(files);
			out = new BufferedWriter(fileWriter);
            out.write(WaterInformation.csvExportHeaders());

			for(int i = 0; i<numBuildings-1; i++) {
				MainActivity.db.setActiveBuildingID(i);
				water = MainActivity.db.getAllWaterInformation();
				buildingName = MainActivity.db.getActiveBuilding().getBuildingFileName();
				for(WaterInformation w:water) {
					out.write(replaceNull(w.csvExport(buildingName)));
				}
			}
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				out.close();
				error = true;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

		return error;

	}

    private boolean MakeMeterCSV(File file) {
        boolean error = false;
        List<UtilityMeterInformation> um;
        BufferedWriter out = null;
        String buildingName;

        try {
            FileWriter fileWriter = new FileWriter(file);
            out = new BufferedWriter(fileWriter);
            out.write(UtilityMeterInformation.headers);

            for(int i = 0; i<numBuildings-1; i++) {
                MainActivity.db.setActiveBuildingID(i);
                um = MainActivity.db.getAllUtilityMeterInformation();
                buildingName = MainActivity.db.getActiveBuilding().getBuildingFileName();
                for(UtilityMeterInformation w:um) {
                    out.write(replaceNull(w.csvExport(buildingName)));
                }
            }
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
                error = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        return error;
    }
	
	private boolean MakeBuildingCSV(File file) {
		boolean error = false;
		Building building;
		BufferedWriter out = null;
		
		try {
			FileWriter fileWriter = new FileWriter(file);
			out = new BufferedWriter(fileWriter);
			out.write(Building.CSV_HEADERS);

			for(int i = 0; i<numBuildings-1; i++) {
				MainActivity.db.setActiveBuildingID(i);
				building = MainActivity.db.getActiveBuilding();
				out.write(replaceNull(building.csvExport()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				out.close();
				error = true;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		return error;
	}
	
	private boolean MakeMediaCSV(File file) {
		boolean error = false;
		ArrayList<SiteMedia> photos;
		BufferedWriter out = null;
		
		try {
			FileWriter fileWriter = new FileWriter(file);
			out = new BufferedWriter(fileWriter);
			out.write(SiteMedia.csvHeaders);
			photos = MainActivity.db.getBuildingSitePhotos();
			photos.addAll(MainActivity.db.getBuildingSiteAudio());
			for(SiteMedia l:photos) {
				out.write(replaceNull(l.csvExport()));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				out.close();
				error = true;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		return error;
	}
	
	private boolean MakeMeasureCSV(File file) {
		boolean error = false;
		ArrayList<Measure> measures;
		BufferedWriter out = null;
		String buildingName;

		try {
			FileWriter fileWriter = new FileWriter(file);
			out = new BufferedWriter(fileWriter);
			out.write(Measure.CSV_HEADERS);
			measures = MainActivity.db.getMeasureList();
			buildingName = MainActivity.db.getActiveBuilding().getBuildingFileName();
			for(Measure m:measures) {
				out.write(replaceNull(m.csvExport(buildingName)));
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				out.close();
				error = true;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return error;	
	}

    private String replaceNull(String nullString) {
        return nullString.replace("null","");
    }
	


}
