import UIKit
import CoreData

class DetailsViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    @IBOutlet weak var imageViewImage: UIImageView!
    @IBOutlet weak var textFieldLanguage: UITextField!
    @IBOutlet weak var textFieldDeveloper: UITextField!
    @IBOutlet weak var textFieldYear: UITextField!
    @IBOutlet weak var buttonSave: UIButton!
    
    var chosenLanguage = ""
    var chosenUUID : UUID?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Kayitli verileri CoreData'dan cek ve goster.
        if(chosenLanguage != ""){
            
            // Kayit gosterilirken Sava Button'u gorunmez ve tiklanamaz yap.
            buttonSave.isHidden = true
            buttonSave.isEnabled = true
            
            let appDelegate = UIApplication.shared.delegate as! AppDelegate // UI App Delegate eris.
            let context = appDelegate.persistentContainer.viewContext
            let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Languages") // Verileri getir.
            let idString = chosenUUID?.uuidString
            fetchRequest.predicate = NSPredicate(format: "id = %@", idString!) // UUID'ye gore Query yap.
            fetchRequest.returnsObjectsAsFaults = false
            
            do  {
                let results = try context.fetch(fetchRequest)
                if(results.count > 0){
                    for result in results as! [NSManagedObject]{
                        if let language = result.value(forKey: "language") as? String {
                            textFieldLanguage.text = language
                        }
                        if let developer = result.value(forKey: "developer") as? String {
                            textFieldDeveloper.text = developer
                        }
                        if let year = result.value(forKey: "year") as? Int {
                            textFieldYear.text = String(year)
                        }
                        if let image = result.value(forKey: "image") as? Data {
                            let image = UIImage(data: image)
                            imageViewImage.image = image
                        }
                    }
                }
            } catch {
                
            }
        } else { // Yeni kayit olusturulacak.
            // Kayit gosterilirken Sava Button'u gorunur ve tiklanamaz yap.
            buttonSave.isHidden = false
            buttonSave.isEnabled = false            
        }
        
        // Ekranda her hangi bir yere tiklanmayi algila.
        let gestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(hideKeyboard))
        view.addGestureRecognizer(gestureRecognizer )
        
        // Kullanici gorsele tiklayabilsin.
        imageViewImage.isUserInteractionEnabled = true
        
        // Ekranda image view uzerine tiklamayi algila.
        let imageGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(selectImage))
        imageViewImage.addGestureRecognizer(imageGestureRecognizer)
    }
    
    // Klavyeyi kapat.
    @objc func hideKeyboard(){
        view.endEditing(true)
    }
    
    // Image secmek icin kullanici media'larina eris.
    @objc func selectImage(){
       let picker = UIImagePickerController() // Media erisimi icin kullanilan bir sinif.
        picker.delegate = self // Picker fonksiyonlarini cagiracak bir ortam olustur.
        picker.sourceType = .photoLibrary // Veri kaynagini belirt.
        picker.allowsEditing = true // Secili media duzenlenebilir.
        present(picker, animated: true, completion: nil)
    }
    
    // Media secimi tamamlandiktan sonra image'i image view'a aktar.
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        imageViewImage.image = info[.originalImage] as? UIImage
        self.dismiss(animated: true, completion: nil)
        
        // Image secildikten sonra Save Button'u tiklanabilir yap.
        buttonSave.isEnabled = true
    }
    
    @IBAction func save(_ sender: Any) {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate // UI App Delegate eris.
        let context = appDelegate.persistentContainer.viewContext
        
        
        let saveData = NSEntityDescription.insertNewObject(forEntityName: "Languages", into: context)
        
        // Save Attributes - Verileri kaydet.
        saveData.setValue(textFieldLanguage.text , forKey: "language")
        saveData.setValue(textFieldDeveloper.text, forKey: "developer")
        if let year = Int(textFieldYear.text!){
            saveData.setValue(year, forKey: "year")
        }
        
        // UUID otomatik olustur.
        saveData.setValue(UUID(), forKey: "id")
        
        // Image verisini al ve kucult.
        let data = imageViewImage.image!.jpegData(compressionQuality: 0.5)
        saveData.setValue(data, forKey: "image")
        
        do {
            try context.save()
            print("Success!")
        } catch {
            print("Error!")
        }
        
        // Diger view controller'a yeni veri eklendigi mesajini yolla ve getData fonksiyonunu tetikle.
        NotificationCenter.default.post(name : Notification.Name("newData"),object: nil)
        // Yeni kayit islemi tamamlandiktan sonra onceki view controller'a geri don.
        self.navigationController?.popViewController(animated: true)
    }
}
