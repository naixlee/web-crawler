import argparse
import os
from avro.io import DatumWriter, DatumReader
from avro.datafile import DataFileWriter, DataFileReader
import avro.schema
import codecs


parser = argparse.ArgumentParser(description='upload web page sources to hdfs following a specified schema')
parser.add_argument('-d', '--directory', help='root directory with web page sources', required=True, type=str)
parser.add_argument('-s', '--schema', help='the avro schema output files following', required=True, type=str)
parser.add_argument('-o', '--output', help='the output file', required=True, type=str)
parser.add_argument('-i', '--seed', help='the seed files', required=True, type=str)

args = parser.parse_args()
input_dir = args.directory
schema_file = args.schema
output_file = args.output
seed_file = args.seed

seed_file_handler = open(seed_file, 'r')
seed_content = seed_file_handler.readline()
company_info = {}
for line in seed_content:
	strs = line.split('\t')
	if len(strs) < 3:
		continue
	company_id = strs[1]
	company_name = strs[2]
	company_cik = strs[0]
	company_info[company_id] = company_name + '@' + company_cik
seed_file_handler.close()

schema = avro.schema.parse(open(schema_file).read())

writer = DataFileWriter(open(output_file, 'w'), DatumWriter(), schema)
dirs = os.listdir(input_dir)

for subdir in dirs:
	print subdir
	if os.path.isdir(input_dir + '/' + subdir):
		print 'dir: ', subdir
		for f in os.listdir(input_dir + '/' + subdir):
			print f
			if os.path.isfile(input_dir + '/' + subdir + '/' + f) and f.endswith('.html'):
				item = {}
				content = codecs.open(input_dir + '/' + subdir + '/' + f, 'r', 'utf-8')
				company_id = subdir

				temp = f[0: f.find('.html')].split('-')
				year = temp[0]
				quarter = temp[1]

				item['li_company_id'] = company_id
				item['filing_year'] = year
				item['filing_quarter'] = quarter

				if company_info.has_key(company_id):
					info = company_info[company_id].split('@')
					item['name'] = info[0]
					item['cik'] = info[1]
				item['page_source'] = content.read()
				writer.append(item)
writer.close()
