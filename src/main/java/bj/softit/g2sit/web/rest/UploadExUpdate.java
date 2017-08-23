package bj.softit.g2sit.web.rest;

import bj.softit.g2sit.domain.Categorie;
import bj.softit.g2sit.domain.Produits;
import bj.softit.g2sit.domain.Stock;
import bj.softit.g2sit.service.CategorieService;
import bj.softit.g2sit.service.ProduitsService;
import bj.softit.g2sit.service.StockService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UploadExUpdate {

    private final Logger log = LoggerFactory.getLogger(UploadExUpdate.class);
    private final CategorieService categorieService;
    private final ProduitsService produitsService;
    private final StockService stockService;

    public UploadExUpdate(CategorieService categorieService, ProduitsService produitsService, StockService stockService) {
        this.categorieService = categorieService;
        this.produitsService = produitsService;
        this.stockService = stockService;
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file ) {

        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            String date= new Date().toString();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(String.valueOf("./Upload/"+file.getOriginalFilename()));
//            Path path = Paths.get("C:\\WORKSPACE/" + file.getOriginalFilename());
            Files.write(path, bytes);
            log.debug("file uploade : {}", file.getOriginalFilename());
            System.out.println("message"+
                "You successfully uploaded '" + file.getOriginalFilename() + "'");
            List<Categorie> categorieList =new ArrayList<>();
            Categorie catsave;
            int i=1;
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);
            while (i <= worksheet.getLastRowNum()) {
                // une instance de categorie
                Categorie categorie = new Categorie();
                // parcourie les cellues
                XSSFRow row = worksheet.getRow(i++);
                if (!isCellEmpty(row.getCell(0))){

                    categorie.setNameCategorie(row.getCell(0).getStringCellValue());
                }
                 if (!isCellEmpty(row.getCell(1))){

                     categorie.setFournisseur(row.getCell(1).getStringCellValue());
                 }
                 if (!isCellEmpty(row.getCell(2))){

                     categorie.setDescription(row.getCell(2).getStringCellValue());
                 }

                // categorieList.add(categorie);
                System.out.println(categorie);
                log.info("Lire categorie  : {}", categorie);
                // verifier si la categorie existe
                if(categorieService.findByNameCategorie(categorie.getNameCategorie()) != null){
                    catsave =categorieService.findByNameCategorie(categorie.getNameCategorie());
                    log.info(" Categorie find : {}", catsave);
                }else {
                    catsave= categorieService.save(categorie);
                    log.info(" Categorie save  : {}", catsave);
                }

                //log.info(" Categorie save finaly  : {}", catsave);
                // une instance de produit

                Produits produits = new Produits();
                if (!isCellEmpty(row.getCell(3))){

                    produits.setNameProduit(row.getCell(3).getStringCellValue());
                }
                if (!isCellEmpty(row.getCell(3))){

                    produits.setNameProduit(row.getCell(3).getStringCellValue());
                }
                if (!isCellEmpty(row.getCell(4))){

                    produits.setReference(""+row.getCell(4).getNumericCellValue());
                }
                if (!isCellEmpty(row.getCell(5))){

                    produits.setDescription(row.getCell(5).getStringCellValue());
                }
                produits.setCategorie(catsave);
                Produits produitsave;
                log.info("Lire produit  : {}", produits);
                if(produitsService.findByProduitName(produits.getNameProduit()) != null){
                    produitsave= produitsService.findByProduitName(produits.getNameProduit());
                    log.info(" Produit find : {}", produitsave);
                }else {
                    produitsave  = produitsService.save(produits);
                    log.info(" Produit save : {}", produitsave);
                }
              //  log.info("Produit save  : {}", produitsave);
                System.out.println("Produit save "+produitsave);

                Stock stock =new Stock();
                if (!isCellEmpty(row.getCell(6))){

                    stock.setQuantite(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
                }

                if (!isCellEmpty(row.getCell(7))){
                    ///Date d1 = Date.
                  /* final DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
                   LocalDate localDate= dtf.parseLocalDate(row.getCell(7).getStringCellValue());*/
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
//                    org.joda.time.format.DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MMM-dd");
                  //  org.joda.time.LocalDate localDate= format.parseLocalDate(row.getCell(7).getStringCellValue());
                   // LocalDate lDate = LocalDate.parse(row.getCell(7).getStringCellValue(),format);
//                    LocalDate localDate = LocalDate.parse( row.getCell(7).getDateCellValue().toString(), formatter);
//                    stock.setDateentrer(localDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1));
                    stock.setDateentrer(ZonedDateTime.ofInstant(row.getCell(7).getDateCellValue().toInstant(), ZoneId.systemDefault()));
                    log.info("stock date  : {} ",  stock.getDateentrer());
                   // stock.setComment(row.getCell(7).getStringCellValue());

                }if (!isCellEmpty(row.getCell(8))){

                    stock.setComment(row.getCell(8).getStringCellValue());
                }
                stock.setProduit(produitsave);
                Stock stocksave ;
                if(stockService.findByProduitName(produits.getNameProduit()) != null){
                    stocksave = stockService.findByProduitName(produits.getNameProduit());
                    stocksave = stockService.save(stocksave.quantite(stocksave.getQuantite().add(stock.getQuantite())));
                    log.info("stock find and save  : {}", stocksave);
                }else {

                    stocksave = stockService.save(stock);
                    log.info("stock save  : {}", stocksave);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "succes";
    }

    public static boolean isCellEmpty(final XSSFCell cell) {
        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return true;
        }

        if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().isEmpty()) {
            return true;
        }

        return false;
    }
}
