
import xml.etree.ElementTree as ET
from lxml import etree
import sys
import argparse


def read_parse_file(file_path):
    with open(file_path, 'rb') as file:
            return etree.fromstring(file.read()) 

def write_file(path, xml_tree):
    with open(path, 'w', encoding='utf-8') as file:
        file.write(ET.tostring(xml_tree, encoding='unicode'))  

def get_parsing_args():
    args = sys.argv
    if len(args) < 3:
        raise Exception('Insufficient arguments provided')
    
    parser = argparse.ArgumentParser()
    parser.add_argument('--input', type=str, required=True, help='Input XML file')
    parser.add_argument('--output', type=str, required=True, help='Output file for results')
    parser.add_argument('-q', '--query', type=str, required=False, help='XPath query')

    args = parser.parse_args()

    args_obj = vars(args)

    input, output, query = args_obj['input'], args_obj['output'], args_obj['query']

    return input, output, query

def query_data(xml, query: str):
    query_result = xml.xpath(query)
    print(len(query_result))
    if(len(query_result) == 0):
        print()
        print("No Result")
        print()
        return

    print("-----------------------XPath Result------------------------")
    for index in range(len(query_result)):
        if isinstance(query_result[index], str):
            print( f"Element-{index}='{query_result[index]}'\n")
        else:
            print(
                f"Element-{index}='{etree.tostring(query_result[index], encoding='unicode')}'\n"
            )
    print("-----------------------------------------------------------")


def main():
    input, output, query = get_parsing_args()

    xml_tree = read_parse_file(input)

    if query is not None:
        query_data(xml_tree, query)

    write_file(output, xml_tree)

if __name__=="__main__":
    main()


        


    