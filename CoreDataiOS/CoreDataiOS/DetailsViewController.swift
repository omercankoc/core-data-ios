import UIKit

class DetailsViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    @IBOutlet weak var imageViewImage: UIImageView!
    @IBOutlet weak var textFieldLanguage: UITextField!
    @IBOutlet weak var textFieldDeveloper: UITextField!
    @IBOutlet weak var textFieldYear: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
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
    }
    
    @IBAction func save(_ sender: Any) {
        
    }
}
