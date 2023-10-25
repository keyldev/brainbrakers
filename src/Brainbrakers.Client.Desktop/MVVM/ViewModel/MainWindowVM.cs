using Brainbrakers.Client.Desktop.Core;
using System.Diagnostics;

namespace Brainbrakers.Client.Desktop.MVVM.ViewModel;

public class MainWindowVM
{

    public RelayCommand ShowDebugMessage { get; set; }

    public MainWindowVM()
    {
        ShowDebugMessage = new RelayCommand(o =>
        {
            Debug.WriteLine("Some message");
        });
    }
}