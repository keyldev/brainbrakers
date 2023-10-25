using Avalonia.Controls;
using Brainbrakers.Client.Desktop.MVVM.ViewModel;

namespace Brainbrakers.Client.Desktop
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            DataContext = new MainWindowVM();
        }
    }
}