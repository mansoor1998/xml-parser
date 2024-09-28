using System;
using System.Xml.Linq;
using System.Xml.XPath;
using CommandLine;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace csharp;

// options for parsed values.
class Options
{
    [Option('i', "input", Required = true, HelpText = "input file.")]
    public string inputFile { get; set; }

    [Option('o', "output", Required = true, HelpText = "output file.")]
    public string outputFile { get; set; }

    [Option('q', "query", Required = false, HelpText = "query")]
    public string query { get; set; }
}

class Program
{
    static XDocument ReadParseFile(string filePath)
    {
        using (FileStream fileStream = new FileStream(filePath, FileMode.Open, FileAccess.Read))
        {
            return XDocument.Load(fileStream);
        }
    }

    static void WriteFile(string path, XDocument xmlTree)
    {
        string directory = Path.GetDirectoryName(path);
        
        if (!Directory.Exists(directory))
        {
            Directory.CreateDirectory(directory);
        }

        using (StreamWriter file = new StreamWriter(path, false, System.Text.Encoding.UTF8))
        {
            xmlTree.Save(file);
        }
    }

    static void QueryData(XDocument xmlTree, string query)
    {
        var result = xmlTree.XPathEvaluate(query);
        
        List<string> queryResults = new List<string>();
        
        if (result is IEnumerable<object> enumerable)
        {
            foreach (var item in enumerable)
            {
                if (item is XText text)
                {
                    queryResults.Add(text.ToString());
                }
                else if (item is XAttribute attribute)
                {
                    queryResults.Add(attribute.ToString());
                }
                else if (item is XElement element)
                {
                    queryResults.Add(element.ToString());
                }
            }
        }
        else if (result is string singleResult)
        {
            queryResults.Add(singleResult);
        }
        
        if (!queryResults.Any())
        {
            Console.WriteLine("\nNo Result\n");
            return;
        }
        
        Console.WriteLine("-----------------------XPath Result------------------------");
        int index = 0;
        foreach (var element in queryResults)
        {
            Console.WriteLine($"Element-{index}='{element}'\n");
            index++;
        }
        Console.WriteLine("-----------------------------------------------------------");
    }

    static void Main(string[] args)
    {
        try
        {
            var options = new Options();
            Parser.Default.ParseArguments<Options>(args)
                .WithParsed<Options>(o =>
                {
                    var (input, output, query) = (o.inputFile, o.outputFile, o.query);
                       
                    XDocument xmlTree = ReadParseFile(input);
                       
                    if (!string.IsNullOrEmpty(query))
                    {
                        QueryData(xmlTree, query);
                    }
                       
                    WriteFile(output, xmlTree);
                });
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error: {ex.Message}");
        }
    }
}