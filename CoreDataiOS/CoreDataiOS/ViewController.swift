import UIKit
import CoreData

class ViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    @IBOutlet weak var tableViewLanguages: UITableView!
    
    var languageArray = [String]()
    var uuidArray = [UUID]()
    
    var selectedLanguage = ""
    var selectedUUID : UUID?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableViewLanguages.delegate = self
        tableViewLanguages.dataSource = self
        
        // Language eklemek icin Navigation Bar'da button olustur.
        navigationController?.navigationBar.topItem?.rightBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonItem.SystemItem.add, target: self, action: #selector(addLanguage))
        
        getData()
    }
    
    // View Controller her acildiginda calistir ve gelen mesaji kontrol et, table view guncelle.
    override func viewWillAppear(_ animated: Bool) {
        NotificationCenter.default.addObserver(self, selector: #selector(getData), name: NSNotification.Name(rawValue: "newData"), object: nil)
    }
    
    // Yeni kayit olustur.
    @objc func addLanguage(){
        selectedLanguage = "" // Yeni kayit olusturulacak.
        performSegue(withIdentifier:  "toDetailsViewController", sender: nil)
    }
    
    // Core Data'dan verileri cek.
    @objc func getData(){
        
        // Goruntulenecek dizileri temizle.
        languageArray.removeAll(keepingCapacity: false)
        uuidArray.removeAll(keepingCapacity: false)
        
        let appDelegate = UIApplication.shared.delegate as! AppDelegate // UI App Delegate eris.
        let context = appDelegate.persistentContainer.viewContext // DB erisimi icin context tanimla.
        
        // Verileri getir.
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Languages")
        fetchRequest.returnsObjectsAsFaults = false // Cache okumasini kapat.
        
        do {
            let results = try context.fetch(fetchRequest)
            if(results.count > 0){
                for result in results as! [NSManagedObject]{
                    // Language verileri al ve ilgili listeye ekle.
                    if let language = result.value(forKey: "language") as? String {
                        self.languageArray.append(language)
                    }
                    // ID verilerini al ve ilgili listeye ekle.
                    if let id = result.value(forKey: "id") as? UUID {
                        self.uuidArray.append(id)
                    }
                    
                    // TableView'a yeni veri geldigi icin guncelle.
                    self.tableViewLanguages.reloadData()
                }
            }
            print("Success get data!")
        } catch {
            print("Error get data!")
        }
    }
    
    // Segue baslamadan once kontrolleri ve atamalari gerceklestir.
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if(segue.identifier == "toDetailsViewController"){
            let destinationVC = segue.destination as! DetailsViewController
            destinationVC.chosenLanguage = selectedLanguage
            destinationVC.chosenUUID = selectedUUID
        }
    }
    
    // Row sayisini elde et.
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return languageArray.count
    }
    
    // Row icerigini elde et.
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.textLabel?.text = languageArray[indexPath.row]
        return cell
    }
    
    // Tiklanan Row'un detay sayfasina git.
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        selectedLanguage = languageArray[indexPath.row]
        selectedUUID = uuidArray[indexPath.row]
        performSegue(withIdentifier: "toDetailsViewController", sender: nil)
    }
    
    // Saga cekilen veriyi sil.
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if(editingStyle == .delete){
            let appDelegate = UIApplication.shared.delegate as! AppDelegate // UI App Delegate eris.
            let context = appDelegate.persistentContainer.viewContext
            let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Languages")
            let idString = uuidArray[indexPath.row].uuidString
            fetchRequest.predicate = NSPredicate(format: "id = %@", idString)
            fetchRequest.returnsObjectsAsFaults = false
            do {
                let results = try context.fetch(fetchRequest)
                if results.count > 0{
                    for result in results as! [NSManagedObject]{
                        if let id = result.value(forKey: "id") as? UUID{
                            if id == uuidArray[indexPath.row]{
                                context.delete(result)
                                uuidArray.remove(at: indexPath.row)
                                languageArray.remove(at: indexPath.row)
                                self.tableViewLanguages.reloadData()
                                
                                do {
                                    try context.save()
                                } catch {
                                    print("Saved Error!")
                                }
                                break
                            }
                        }
                    }
                }
            } catch {
                print("Deleted Error!")
            }
        }
    }
}

