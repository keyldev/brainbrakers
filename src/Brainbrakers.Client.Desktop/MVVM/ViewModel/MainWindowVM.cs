using System;
using Brainbrakers.Client.Desktop.Core;
using System.Diagnostics;
using System.Net.Http;
using System.Net.Mime;
using System.Text;
using System.Text.Json;

namespace Brainbrakers.Client.Desktop.MVVM.ViewModel;

public class MainWindowVM : ObservableObject
{
    public RelayCommand ShowDebugMessage { get; set; }

    private string _helloWorldText;

    public string HelloWorldText
    {
        get { return _helloWorldText; }
        set
        {
            _helloWorldText = value;
            OnPropertyChanged();
        }
    }

    public MainWindowVM()
    {
        ShowDebugMessage = new RelayCommand(async o =>
        {
            var client = new HttpClient();
            client.BaseAddress = new Uri("http://localhost:5000/api/v1/");

            var json = new StringContent(JsonSerializer.Serialize(new
            {
                Username = "keyl",
                Password = "ba4b9e011dd1d9be3fed74b1f7f0d11993d6d490b393585100fc64610a69e0c9"
            }), Encoding.UTF8, MediaTypeNames.Application.Json);
            var request = await client.PostAsync("auth/login", json);
            var response = await request.Content.ReadAsStringAsync();

            Debug.WriteLine(response);
            
            HelloWorldText = response + " alalall";
        });
    }
}