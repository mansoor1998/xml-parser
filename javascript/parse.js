const xml2js = require('xml2js')
const fs = require('fs');
const yargs = require('yargs');
const { DOMParser, XMLSerializer } = require('xmldom');
const xpath = require('xpath');


// it gives the result as an object
const readAndParseFile = (filePath) => {

    const data = fs.readFileSync(filePath, { encoding: 'utf8', flag: 'r' });

    const xmlPromise = new Promise((res, rej) => {
        xml2js.parseString(data, (err, result) => {
            if (err) {
                rej(err)
            }
    
            res(result);        
        });    
    });

    return xmlPromise;
}

const writeFile = (json_object, file_path) => {

    const builder = new xml2js.Builder();

    const xmlString = builder.buildObject(json_object);

    fs.writeFile(file_path, xmlString, (err) => {
        if(err)
            console.log('unable to write the file in node js');
    })
}

const getParsingArgs = () => {

    const args = yargs
    .option('input', {
        description: 'Input XML file',
        type: 'string',
        demandOption: true
    })
    .option('output', {
        description: 'Output file for results',
        type: 'string',
        demandOption: true,
    })
    .option('query', {
        alias: 'q',
        description: 'XPath query',
        type: 'string',
        default: '',
    })
    .argv

    return [ args['input'], args['output'], args['q'] ];
}


const query_data = (xmlData, query) => {

    const xmlString = new xml2js.Builder().buildObject(xmlData);

    const doc = new DOMParser().parseFromString(xmlString);

    const serializer = new XMLSerializer();

    const nodes = xpath.select(query, doc);

    if(nodes.length == 0){
        console.log('EMPTY RESULT');
    }

    console.log(nodes.length)

    console.log("-----------------------Query Results---------------------------------");

    nodes.forEach(node => {
        const xmlOutput = serializer.serializeToString(node);
        console.log(xmlOutput);
    });

    console.log("-----------------------Query Results---------------------------------");
}

async function main() {
    const [ input_path, output_path, q ] = getParsingArgs();

    const result = await readAndParseFile(input_path);

    if(q)
        query_data(result, q);

    writeFile(result, output_path)
}


main();