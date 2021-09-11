import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var tableViewLanguages: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Language eklemek icin Navigation Bar'da button olustur.
        navigationController?.navigationBar.topItem?.rightBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonItem.SystemItem.add, target: self, action: #selector(addLanguage))
    }

    @objc func addLanguage(){
        performSegue(withIdentifier:  "toDetailsViewController", sender: nil)
    }
}

