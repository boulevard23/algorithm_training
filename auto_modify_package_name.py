import os
import re

def getBasePath():
  current_path = os.path.abspath('.')
  base_path = os.path.basename(current_path)
  return base_path

def getSrcPath():
  current_path = os.path.abspath('.')
  src_path = os.path.join(current_path, 'src')
  return src_path

def visit(arg, dirname, names):
  for name in names:
    subname = os.path.join(dirname, name)
    if subname.endswith('.java'):
      #print subname
      m = arg.search(subname)
      #print m.groups()[0]
      pkg_name = 'package ' + m.groups()[0].replace('/', '.') + ';\n'

      modifyPkgName(subname, pkg_name)

def modifyPkgName(file_name, pkg_name):
  with open(file_name, 'r') as file:
    lines = file.readlines()

  if lines[0] == pkg_name: return

  with open(file_name, 'w') as file:
    if lines[0].startswith('package'):
      lines[0] = pkg_name
    else:
      file.write(pkg_name)
      file.write('\n')

    for line in lines:
      file.write(line)

pattern = re.compile(r'/java/(.+)/.+.java$')
os.path.walk(getSrcPath(), visit, pattern)
