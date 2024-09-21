const xml2js = require('xml2js')
const fs = require('fs');


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
    const args = process.argv;
    
    console.log(args);

    if( args.length < 3 ) {
        console.error('not enough args are defined.', 'You should have 2 file paths, an incoming file path and uploading file path');
        return;
    }

    return [ args[2], args[3] ];
}


async function main() {
    const [ input_path, output_path ] = getParsingArgs();

    const result = await readAndParseFile(input_path);

    writeFile(result, output_path)
}



main();
