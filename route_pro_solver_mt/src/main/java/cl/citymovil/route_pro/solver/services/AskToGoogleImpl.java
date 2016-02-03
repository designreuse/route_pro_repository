package cl.citymovil.route_pro.solver.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;

import cl.citymovil.route_pro.solver.domain.Location;
import cl.citymovil.route_pro.solver.domain.LocationTmp;
import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.LocationContainerForGoogleAsk;
import cl.citymovil.route_pro.solver.util.RelationLocation;

@Service
public class AskToGoogleImpl implements AskToGoogle{

	protected final Log logger = LogFactory.getLog(getClass());

	protected final int MaxConsultGoogle = 25+1;
	
	@Override
	public ArrayList<RelationLocation> getDistanceByGoogleAlpha(
			LocationContainerForGoogleAsk locationContainerForGoogle) {
		logger.info("\n[(getDistanceByGoogleAlpha)]start getDistanceByGoogleAlpha \n");
		GeoApiContext context = new GeoApiContext().setEnterpriseCredentials("gme-bigservicespacityplanning","j-NB2bOlmRUMInQ459WVwrf7O9w=");
		////////////////////Definición de Variables //////////////////		
		ArrayList<RelationLocation> listRelationLocationDeRetorno = new ArrayList<RelationLocation>();
		List<Location> listDestLocations = locationContainerForGoogle.getLocationDestiny();
		List<Location> listOrigLocations = locationContainerForGoogle.getLocationOrigin();
		Location originLocation = new Location(); 
		Integer sizeListOrigLocation = listOrigLocations.size();
		Integer sizeListDestLocation = listDestLocations.size();
		ArrayList<DistanceMatrix> resultsList = new ArrayList<DistanceMatrix>();
		int countOfLocation = 1;
		int countOfQueryGoogle = 0;
//		int countOfLocationOrigin = 1;
//		int countOfQueryGoogleOrigin = 0;

//		ArrayList <ArrayList <Location>> listOfListLocationOrigins = new ArrayList <ArrayList <Location>>();
		ArrayList <ArrayList <Location>> listOfListLocationDestiny = new ArrayList <ArrayList <Location>>();
//		ArrayList <Location> listOfOriginsLocation = new ArrayList <Location>();
		ArrayList <Location> listOfDestinyLocation = new ArrayList <Location>();
		List<String> newPositionStringList = new ArrayList<String>();
		List<String> oldPositionStringList = new ArrayList<String>();
		String positionStringNEW = new String();
		String positionStringOLD = new String();
		String[] origenPosition;
		String[] destinyPosition;
		List<String[]> origenLocationsList = new ArrayList<String[]>();
		List<String[]> destinyLocationsList = new ArrayList<String[]>();
		Boolean flag = true;
//		Boolean flagOrigins = true;
		//////////////////// FIN Variables definitivas//////////////////
		
		/**TRATAMIENTO DE ORIGENES**/
		//todas las listas de origenes debe ser solo de un maximo de un origen por lista, sino el sistema se sale ya que no esta programado para realizar sus funciones de otro modo
		if(sizeListOrigLocation==1){
			/**
//			 * DATOS OBTENIDOS DEL PROCESO ANTERIOR PARA GENERAR EL
//			 * RELATIONLOCATION
//			 **/
//			System.out.println("ESTO ES ORIGEN en 1");
			
			originLocation.setLatitude(listOrigLocations.get(0).getLatitude());
			originLocation.setLongitude(listOrigLocations.get(0).getLongitude());
			originLocation.setLocationId(listOrigLocations.get(0).getLocationId());
//			System.out.println("mostrando el contenido del origen, Latitud:"+originLocation.getLatitude()+" Long:"+originLocation.getLongitude()+" ID:"+originLocation.getLocationId());

			//listOfOriginsLocation.add(0, originLocation);
			
			/** DATOS PARA GENERAR LA CONSLUTA DE GOOGLE **/
			// RECOPILO LOS DATOS DE LAT Y LNG, LOS ALMACENO EN UN LIST Y
			// POSTERIORMENTE LOS TRANSFORMO EN UN ARRAY
			// DE ACUERDO A LO REQUERIDO POR GOOGLE
			
			positionStringNEW = listOrigLocations.get(0).getLatitude() + ","+ listOrigLocations.get(0).getLongitude();
			newPositionStringList.add(positionStringNEW);
			origenPosition = newPositionStringList.toArray(new String[newPositionStringList.size()]);
//			
//			logger.trace("[(getDistanceByGoogleAlpha)] CONTADOR DE ARRAY MAX LENGTH = MaxConsultGoogle -> countOfQueryGoogleOrigin: " + countOfQueryGoogleOrigin);
			
			/**Almacenamiento de list de list Location para generar el RelationLocation**/
//			listOfListLocationOrigins.add( listOfOriginsLocation);
			
			/**almacenamiento de los origen list Location que contiene los string que realizan la consulta a Google**/
			origenLocationsList.add(origenPosition);
			
			
		}else{
//			System.out.println("SALIO PORQUE NO TIENE LARGO 1, SINO QUE TIENE LARGO:"+sizeListOrigLocation);
			return null;
		}

		
//		for (int contOrigLoc = 0; contOrigLoc < sizeListOrigLocation; contOrigLoc++) { 
//			/**
//			 * DATOS OBTENIDOS DEL PROCESO ANTERIOR PARA GENERAR EL
//			 * RELATIONLOCATION
//			 **/
//			Location originLocation = new Location(); 
//			originLocation.setLatitude(listOrigLocations.get(contOrigLoc).getLatitude());
//			originLocation.setLongitude(listOrigLocations.get(contOrigLoc).getLongitude());
//			originLocation.setLocationId(listOrigLocations.get(contOrigLoc).getLocationId());
//			listOfOriginsLocation.add(contOrigLoc, originLocation);
//			
//			/** DATOS PARA GENERAR LA CONSLUTA DE GOOGLE */
//			// RECOPILO LOS DATOS DE LAT Y LNG, LOS ALMACENO EN UN LIST Y
//			// POSTERIORMENTE LOS TRANSFORMO EN UN ARRAY
//			// DE ACUERDO A LO REQUERIDO POR GOOGLE
//			
//			positionStringNEW = listOrigLocations.get(contOrigLoc).getLatitude() + ","
//					+ listOrigLocations.get(contOrigLoc).getLongitude();
//			
//			newPositionStringList.add(positionStringNEW);
//			origenPosition = newPositionStringList.toArray(new String[newPositionStringList.size()]);
//			logger.trace("[(getDistanceByGoogleAlpha)] CONTADOR DE ARRAY MAX LENGTH = MaxConsultGoogle -> countOfQueryGoogleOrigin: " + countOfQueryGoogleOrigin);
//
//			if (countOfLocationOrigin >= MaxConsultGoogle) {// si hay mas de 25 locaciones de origen
//				/**Almacenamiento de list de list Location para generar el RelationLocation**/
//				listOfListLocationOrigins.add(countOfQueryGoogleOrigin, listOfOriginsLocation);
//				
//				/**almacenamiento de los origen list Location que contiene los string que realizan la consulta a Google**/
//				origenLocationsList.add(countOfQueryGoogleOrigin, origenPosition);
//				countOfLocationOrigin = 1;
//				countOfQueryGoogleOrigin++;
//				flagOrigins = false;
//			} else {
//				flagOrigins = true;
//			}
//			countOfLocationOrigin++;
//			
//		}
//
//		if (flagOrigins == true) {
//			listOfListLocationOrigins.add(countOfQueryGoogleOrigin, listOfOriginsLocation);
//			
//			origenPosition = newPositionStringList.toArray(new String[newPositionStringList.size()]);
//			origenLocationsList.add(countOfQueryGoogleOrigin, origenPosition);
//		}
		
		
		
		/** PROCESAMIENTO DE LOCATION DESTINY **/
		// genero la lista de consulta con entre la nueva locacion y las que ya estan

		for (int contOldLoc = 0; contOldLoc < sizeListDestLocation; contOldLoc++) {
//			System.out.println("contador de destino:"+contOldLoc);
//			System.out.println("countOfLocation:"+countOfLocation);
			/**
			 * DATOS OBTENIDOS DEL PROCESO ANTERIOR PARA GENERAR EL
			 * RELATIONLOCATION
			 **/
			
			Location destinyLocation = new Location();
			destinyLocation.setLatitude(listDestLocations.get(contOldLoc).getLatitude());
			destinyLocation.setLongitude(listDestLocations.get(contOldLoc).getLongitude());
			destinyLocation.setLocationId(listDestLocations.get(contOldLoc).getLocationId());
//			System.out.println("*****ID destino incluido:"+listDestLocations.get(contOldLoc).getLocationId());
			listOfDestinyLocation.add( destinyLocation);

			/** DATOS PARA GENERAR LA CONSLUTA DE GOOGLE */
			// RECOPILO LOS DATOS DE LAT Y LNG, LOS ALMACENO EN UN LIST Y
			// POSTERIORMENTE LOS TRANSFORMO EN UN ARRAY
			// DEACUERDO A LO REQUERIDO POR GOOGLE

			positionStringOLD = listDestLocations.get(contOldLoc).getLatitude() + ","
					+ listDestLocations.get(contOldLoc).getLongitude();
			oldPositionStringList.add(positionStringOLD);
			destinyPosition = oldPositionStringList.toArray(new String[oldPositionStringList.size()]);
			logger.trace("[(getDistanceByGoogleAlpha)]oldPositionStringList countOfQueryGoogle: " + countOfQueryGoogle);

			
			/**
			 * EMPAQUETADO PARA CONSULTA A GOOGLE CON ARRAY LONG DE
			 * LARGO=MaxConsultGoogle
			 **/
			// verifico si se ha llegado al largo de 25
			if (countOfLocation >= MaxConsultGoogle) {// newPositionStringList.size()==25
//				System.out.println("ENTRO AL MAXXXXXXXXXXXXX ");
//				
//				System.out.println("countOfLocation"+countOfLocation);
//				System.out.println("countOfQueryGoogle"+countOfQueryGoogle);
//				System.out.println("listOfDestinyLocation.size()"+listOfDestinyLocation.size());
//				System.out.println("destinyPosition.length()"+destinyPosition.length);
				
				/**Almacenamiento de list de list Location para generar el RelationLocation**/
				
				//CON ESTE VERIFICO LOS ID DE DESTINO
				listOfListLocationDestiny.add(countOfQueryGoogle, listOfDestinyLocation);//<<<<<<< esta fallando, tiene dos array de 29, y debiera ser 1 de 25 y otro de 4
				
				//ESTE VA A GOOGLE
				destinyLocationsList.add(countOfQueryGoogle, destinyPosition);
				/**
				 * AlMACENAMIENTO DE LAS LOCACIONES EN LA LISTA DE LISTAS DE
				 * RELACIONES DE LOCACIÓN DE LARGO=MaxConsultGoogle
				 **/
				
				listOfDestinyLocation = new ArrayList <Location>();
				countOfLocation = 1;
				countOfQueryGoogle++;
				oldPositionStringList= new ArrayList<String>();
				flag = false;
				
			} else {
				flag = true;
			}
			countOfLocation++;
		}
		/**
		 * EMPAQUETADO PARA CONSULTA A GOOGLE CON ARRAY LONG DE LARGO <
		 * MaxConsultGoogle
		 **/
		if (flag == true) {
			listOfListLocationDestiny.add(countOfQueryGoogle, listOfDestinyLocation);	
			/**
			 * AlMACENAMIENTO DEL RESTO DE LAS LOCACIONES EN LA LISTA DE LISTAS
			 * DE RELACIONES DE LOCACIÓN
			 **/
			destinyPosition = oldPositionStringList.toArray(new String[oldPositionStringList.size()]);
			destinyLocationsList.add(countOfQueryGoogle, destinyPosition);
		}
		/**
		 * Leyendo DATOS PARA GENERAR LA CONSLUTA DE GOOGLE BORRAR ESTO DESPUES
		 */
		logger.trace("[(getDistanceByGoogleAlpha)]destinyLocationsList.size()" + destinyLocationsList.size());
		logger.trace("[(getDistanceByGoogleAlpha)]:::::::::PREGUNTANDO A GOOGLE:::::::::");
		
		/////////////////////////////////////////////////////////////
		
		
		/////////////////////////////////////////////////////////////

		/**** CONSULTANDO A GOOGLE ****/
			// PARA CADA QUERYGOOGLE DE TAMAÑO MAXIMO DE 25
//			if(listOfListLocationOrigins.size() == origenLocationsList.size()){
				logger.trace("[(getDistanceByGoogleAlpha)] Los arreglos listOfListLocationOrigins(para R.L.) y origenLocationsList(Google) tiene el mismo tamaño (OK) ");
			
//				for (int count1 = 0; count1 < listOfOriginsLocation.size(); count1++) {
//					logger.trace("[(getDistanceByGoogleAlpha)]::::Contador de Array de Origen:"+count1);
//					logger.trace("[(getDistanceByGoogleAlpha)] destinyLocationsList.size() :" + destinyLocationsList.size());
				for (int cont2 = 0; cont2 < countOfQueryGoogle+1; cont2++) {
					logger.trace("[(getDistanceByGoogleAlpha)]COUNT1 :0,  COUNT2:" + cont2);
					logger.trace("[(getDistanceByGoogleAlpha)]LISTO PARA CONSULTAR origenLocationsList.get(count1)"
							+ origenLocationsList.get(0).length);
					logger.trace("[(getDistanceByGoogleAlpha)]LISTO PARA CONSULTAR destinyLocationsList.get(count1) "
							+ destinyLocationsList.get(cont2).length);
					
					logger.trace("[(getDistanceByGoogleAlpha)]consultando bloque ," + cont2);
					
					try {
						DistanceMatrix result = DistanceMatrixApi.getDistanceMatrix(context,origenLocationsList.get(0), destinyLocationsList.get(cont2)).await();
						
//						System.out.println("ESTE  ES EL LARGO DE ROW:"+result.rows.length);
//						System.out.println("ESTE  ES EL LARGO DE ELEMENTS:"+result.rows[0].elements.length);
//						System.out.println("ESTE ES EL LARGO DE LA LISTA DE DESTINOS LOCATION:"+listOfListLocationDestiny.get(cont2).size());
							for(int destinCount=0; destinCount <  result.rows[0].elements.length; destinCount++){
								RelationLocation relationLocation = new RelationLocation();
								relationLocation.setIdFirstLocation(originLocation.getLocationId());
								relationLocation.setIdSecondLocation(listOfListLocationDestiny.get(cont2).get(destinCount).getLocationId());
								relationLocation.setGoingDistance((double) result.rows[0].elements[destinCount].distance.inMeters);
								relationLocation.setGoingDuration((double) result.rows[0].elements[destinCount].duration.inSeconds);
								listRelationLocationDeRetorno.add(relationLocation);
							}		
						
						resultsList.add(result);
						
					} catch (Exception e) {
						cont2--;
						e.printStackTrace();
						logger.trace("[(getDistanceByGoogleAlpha)] error!!! -> uncomment printStackTrace() -> DistanceMatrix (Google Problems)");
							
						
						try {
							Thread.sleep(2500);
							
							
						} catch (Exception e2) {
							logger.trace("[(getDistanceByGoogleAlpha)]error!!! -> Thread.sleep() problems");
						}
						
						
					}					
					logger.trace("[(getDistanceByGoogleAlpha)]CONSULTA REALIZADA 0," + cont2);

				}
//			}
//		}else{
//			logger.trace("[(getDistanceByGoogleAlpha)]Sistema Caido.Los arreglos listOfListLocationOrigins(para R.L.) y origenLocationsList(Google) NO tiene el mismo tamaño (FAIL) ");
//		}
			/** COMPLETANDO EL RelationLocation con los DATOS DE GOOGLE **/
			logger.info("\n[(getDistanceByGoogleAlpha)]end getDistanceByGoogleAlpha \n");
			return listRelationLocationDeRetorno;
	}
	@Override
	public ArrayList<RelationLocation> getDistanceByGoogle(LocationContainer locationContainer) {// String[]
																									// newLocations,String[]
																									// oldLocations)
		
		//Activar consultas a API por medio de google Gratuito.
	//	GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyB-ZZHRgGvMLczqzDZnmFBds4Zs27wm1AY");//AIzaSyBc-yEd3hfr4Q9GEVf2uYu_JaGbtLNlt7Y
		Date d = new Date();
		System.out.println("Tiempo Ahora:" + d.toString());
		logger.info("hora: "+ d.toString());
		GeoApiContext context = new GeoApiContext().setEnterpriseCredentials("gme-bigservicespacityplanning","j-NB2bOlmRUMInQ459WVwrf7O9w=");

		// 1- necesito ingresar las nueva locaciones a la tabla de location para
		// obtener su id

		// 2-ordenar todos las locaciones en un nuevo arreglo

		// 3- dividir ese arreglo en secciones de 25 string[]

		// 4-Consultar al Google

		// 5-Retornar un array con todos los nuevos puntos y pasarlos al
		// postProcess

		/*
		 * El almacenamiento de origen y destino se realizan en un (List <List
		 * <Long[]> >), donde el primer List almacena hasta 25 pares de Long que
		 * registran los Id de Origen y destino respectivamente.
		 * 
		 */
		////////////////////Definición de Variables //////////////////
		
		ArrayList<RelationLocation> listRelationLocationDeRetorno = new ArrayList<RelationLocation>();
		List<Location> oldLocations = locationContainer.getLocation();
		List<LocationTmp> newLocations = locationContainer.getLocationTmp();
		Integer sizeNewLocation = newLocations.size();
		Integer sizeOldLocation = oldLocations.size();
		ArrayList<DistanceMatrix> resultsList = new ArrayList<DistanceMatrix>();
		int countOfLocation = 1;
		int countOfQueryGoogle = 0;
		int countOfLocationOrigin = 1;
		int countOfQueryGoogleOrigin = 0;
		ArrayList <ArrayList <Location>> listOfListLocationOrigins = new ArrayList <ArrayList <Location>>();
		ArrayList <ArrayList <Location>> listOfListLocationDestiny = new ArrayList <ArrayList <Location>>();
		ArrayList <Location> listOfOriginsLocation = new ArrayList <Location>();
		ArrayList <Location> listOfDestinyLocation = new ArrayList <Location>();
		List<String> newPositionStringList = new ArrayList<String>();
		List<String> oldPositionStringList = new ArrayList<String>();
		String positionStringNEW = new String();
		String positionStringOLD = new String();
		String[] origenPosition;
		String[] destinyPosition;
		List<String[]> origenLocationsList = new ArrayList<String[]>();
		List<String[]> destinyLocationsList = new ArrayList<String[]>();
		Boolean flag = true;
		Boolean flagOrigins = true;

		//////////////////// FIN Variables definitivas//////////////////

		/********* DESARROLLO *************/

		/** PROCESAMIENTO DE LOCATION ORIGINS **/
		// Genero todos los ORIGENES en array de maximo 25
		for (int contNewLoc = 0; contNewLoc < sizeNewLocation; contNewLoc++) { 
			/**
			 * DATOS OBTENIDOS DEL PROCESO ANTERIOR PARA GENERAR EL
			 * RELATIONLOCATION
			 **/
			Location originLocation = new Location(); 
			originLocation.setLatitude(newLocations.get(contNewLoc).getLatitudeTmp());
			originLocation.setLongitude(newLocations.get(contNewLoc).getLongitudeTmp());
			originLocation.setLocationId(newLocations.get(contNewLoc).getLocationId());
			listOfOriginsLocation.add(contNewLoc, originLocation);

			/** DATOS PARA GENERAR LA CONSLUTA DE GOOGLE */
			// RECOPILO LOS DATOS DE LAT Y LNG, LOS ALMACENO EN UN LIST Y
			// POSTERIORMENTE LOS TRANSFORMO EN UN ARRAY
			// DE ACUERDO A LO REQUERIDO POR GOOGLE
			positionStringNEW = newLocations.get(contNewLoc).getLatitudeTmp() + ","
					+ newLocations.get(contNewLoc).getLongitudeTmp();
			newPositionStringList.add(positionStringNEW);
			origenPosition = newPositionStringList.toArray(new String[newPositionStringList.size()]);
			logger.info("CONTADOR DE ARRAY MAX LENGTH = MaxConsultGoogle -> countOfQueryGoogleOrigin: " + countOfQueryGoogleOrigin);

			if (countOfLocationOrigin >= MaxConsultGoogle) {// si hay mas de 25 locaciones de origen
				/**Almacenamiento de list de list Location para generar el RelationLocation**/
				listOfListLocationOrigins.add(countOfQueryGoogleOrigin, listOfOriginsLocation);
				
				/**almacenamiento de los origen list Location que contiene los string que realizan la consulta a Google**/
				origenLocationsList.add(countOfQueryGoogleOrigin, origenPosition);
				countOfLocationOrigin = 1;
				countOfQueryGoogleOrigin++;
				flagOrigins = false;

			} else {
				flagOrigins = true;
			}
			countOfLocationOrigin++;
		}

		if (flagOrigins == true) {
			listOfListLocationOrigins.add(countOfQueryGoogleOrigin, listOfOriginsLocation);
			
			origenPosition = newPositionStringList.toArray(new String[newPositionStringList.size()]);
			origenLocationsList.add(countOfQueryGoogleOrigin, origenPosition);
		}

		/**
		 * Leyendo DATOS PARA GENERAR LA CONSULTA DE GOOGLE BORRAR ESTO DESPUES
		 */
		logger.info("origenLocationsList.size()" + origenLocationsList.size());
//		for (int i = 0; i < origenLocationsList.size(); i++) {
//
//			System.out.println("origenLocationsList.get(i).length" + origenLocationsList.get(i).length);
//			for (int j = 0; j < origenLocationsList.get(i).length; j++) {
//				System.out.println("origenLocationsList.get(i)" + origenLocationsList.get(i)[j]);
//			}
//		}

		/** PROCESAMIENTO DE LOCATION DESTINY **/
		// genero la lista de consulta con entre la nueva locacion y las que ya estan
		for (int contOldLoc = 0; contOldLoc < sizeOldLocation; contOldLoc++) {
			/**
			 * DATOS OBTENIDOS DEL PROCESO ANTERIOR PARA GENERAR EL
			 * RELATIONLOCATION
			 **/
			Location destinyLocation = new Location();
			destinyLocation.setLatitude(oldLocations.get(contOldLoc).getLatitude());
			destinyLocation.setLongitude(oldLocations.get(contOldLoc).getLongitude());
			destinyLocation.setLocationId(oldLocations.get(contOldLoc).getLocationId());
			listOfDestinyLocation.add(contOldLoc, destinyLocation);

			/** DATOS PARA GENERAR LA CONSLUTA DE GOOGLE */
			// RECOPILO LOS DATOS DE LAT Y LNG, LOS ALMACENO EN UN LIST Y
			// POSTERIORMENTE LOS TRANSFORMO EN UN ARRAY
			// DEACUERDO A LO REQUERIDO POR GOOGLE

			positionStringOLD = oldLocations.get(contOldLoc).getLatitude() + ","
					+ oldLocations.get(contOldLoc).getLongitude();
			oldPositionStringList.add(positionStringOLD);
			destinyPosition = oldPositionStringList.toArray(new String[oldPositionStringList.size()]);
			
			
			logger.info("oldPositionStringList countOfQueryGoogle: " + countOfQueryGoogle);

			
			/**
			 * EMPAQUETADO PARA CONSULTA A GOOGLE CON ARRAY LONG DE
			 * LARGO=MaxConsultGoogle
			 **/
			// verifico si se ha llegado al largo de 25
			if (countOfLocation >= MaxConsultGoogle) {// newPositionStringList.size()==25
				
				/**Almacenamiento de list de list Location para generar el RelationLocation**/
				listOfListLocationDestiny.add(countOfQueryGoogle, listOfDestinyLocation);
				destinyLocationsList.add(countOfQueryGoogle, destinyPosition);
				/**
				 * AlMACENAMIENTO DE LAS LOCACIONES EN LA LISTA DE LISTAS DE
				 * RELACIONES DE LOCACIÓN DE LARGO=MaxConsultGoogle
				 **/
				countOfLocation = 1;
				countOfQueryGoogle++;
				oldPositionStringList.clear();
				flag = false;
			} else {
				flag = true;
			}
			countOfLocation++;
		}

		/**
		 * EMPAQUETADO PARA CONSULTA A GOOGLE CON ARRAY LONG DE LARGO <
		 * MaxConsultGoogle
		 **/
		if (flag == true) {
			listOfListLocationDestiny.add(countOfQueryGoogle, listOfDestinyLocation);	
			/**
			 * AlMACENAMIENTO DEL RESTO DE LAS LOCACIONES EN LA LISTA DE LISTAS
			 * DE RELACIONES DE LOCACIÓN
			 **/
			destinyPosition = oldPositionStringList.toArray(new String[oldPositionStringList.size()]);
			destinyLocationsList.add(countOfQueryGoogle, destinyPosition);
		}
		/**
		 * Leyendo DATOS PARA GENERAR LA CONSLUTA DE GOOGLE BORRAR ESTO DESPUES
		 */
		logger.info("destinyLocationsList.size()" + destinyLocationsList.size());
//		for (int i = 0; i < destinyLocationsList.size(); i++) {
//			logger.info("destinyLocationsList.get(i).length" + destinyLocationsList.get(i).length);
//			for (int j = 0; j < destinyLocationsList.get(i).length; j++) {
//				logger.info("destinyLocationsList.get(i)" + destinyLocationsList.get(i)[j]);
//			}
//		}

		logger.info(":::::::::PREGUNTANDO A GOOGLE:::::::::");

		/**** CONSULTANDO A GOOGLE ****/
			// PARA CADA QUERYGOOGLE DE TAMAÑO MAXIMO DE 25
			if(listOfListLocationOrigins.size() == origenLocationsList.size()){
				System.out.println("Los arreglos listOfListLocationOrigins(para R.L.) y origenLocationsList(Google) tiene el mismo tamaño (OK) ");
			
				for (int count1 = 0; count1 < origenLocationsList.size(); count1++) {
				 logger.info("::::Contador de Array de Origen:"+count1);
				logger.info(" destinyLocationsList.size() :" + destinyLocationsList.size());
				for (int cont2 = 0; cont2 < destinyLocationsList.size(); cont2++) {
					logger.info("COUNT1 :" + count1 + " COUNT2:" + cont2);
					logger.info("LISTO PARA CONSULTAR origenLocationsList.get(count1)"
							+ origenLocationsList.get(count1).length);
					logger.info("LISTO PARA CONSULTAR destinyLocationsList.get(count1) "
							+ destinyLocationsList.get(cont2).length);
//					for (int contOrigin = 0; contOrigin < origenLocationsList.get(count1).length; contOrigin++) {
//						logger.info("contOrgin: " + contOrigin + " Location Origins :"
//								+ origenLocationsList.get(count1)[contOrigin]);
//
//					}
//					for (int contDest = 0; contDest < destinyLocationsList.get(cont2).length; contDest++) {
//						logger.info("contDest: " + contDest + " Location Destiny :"
//								+ destinyLocationsList.get(cont2)[contDest]);
//					}

					logger.info("consultando bloque " + count1 + "," + cont2);
					
					try {
						DistanceMatrix result = DistanceMatrixApi.getDistanceMatrix(context,origenLocationsList.get(count1), destinyLocationsList.get(cont2)).await();
						
						for(int originCount=0; originCount < listOfListLocationOrigins.get(count1).size(); originCount++){
							for(int destinCount=0; destinCount <  listOfListLocationDestiny.get(cont2).size(); destinCount++){
								RelationLocation relationLocation = new RelationLocation();
								relationLocation.setIdFirstLocation(listOfListLocationOrigins.get(count1).get(originCount).getLocationId());
								relationLocation.setIdSecondLocation(listOfListLocationDestiny.get(cont2).get(destinCount).getLocationId());
								relationLocation.setGoingDistance((double) result.rows[originCount].elements[destinCount].distance.inMeters);
								relationLocation.setGoingDuration((double) result.rows[originCount].elements[destinCount].duration.inSeconds);
								listRelationLocationDeRetorno.add(relationLocation);
							}		
						}
						resultsList.add(result);
						
					} catch (Exception e) {
						cont2--;
						//e.printStackTrace();
						logger.error("error!!! -> uncomment printStackTrace() -> DistanceMatrix (Google Problems)");
							
						
						try {
							Thread.sleep(2500);
							
							
						} catch (Exception e2) {
							System.out.println("error!!! -> Thread.sleep() problems");
						}
						
						
					}					
					logger.info("CONSULTA REALIZADA"+ count1 + "," + cont2);

				}
			}
		}else{
			System.out.println("Sistema Caido.Los arreglos listOfListLocationOrigins(para R.L.) y origenLocationsList(Google) NO tiene el mismo tamaño (FAIL) ");
		}

//			for (int x = 0; x < resultsList.size(); x++) {
//				for (int z = 0; z < resultsList.get(x).rows.length; z++) {
//					for (int y = 0; y < resultsList.get(x).rows[z].elements.length; y++) {
//						logger.info(y + "-y) Elemento: " + resultsList.get(x).rows[z].elements[y].distance);
//					}
//				}
//
//			}

		/** COMPLETANDO EL RelationLocation con los DATOS DE GOOGLE **/
		logger.info("Cerrando el Process");
		Date f = new Date();
		logger.info("Tiempo de Término de consultas a Google:" + f.toString());


		return listRelationLocationDeRetorno;
	}

	@Override
	public void getTimeByGoogle() {
		// TODO Auto-generated method stub
		// return null;
	}

	

}
