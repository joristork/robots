"""
Transform a YAML file into a LaTeX Beamer presentation.

Copyright (C) 2009 Arthur Koziel <arthur@arthurkoziel.com>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
"""

from yaml.reader import *
from yaml.scanner import *
from yaml.parser import *
from composer import *
from yaml.constructor import *
from yaml.resolver import *

class PairLoader(Reader, Scanner, Parser, PairComposer, Constructor, Resolver):

    def __init__(self, stream):
        Constructor.add_constructor(u'!omap', self.omap_constructor)
        
        Reader.__init__(self, stream)
        Scanner.__init__(self)
        Parser.__init__(self)
        PairComposer.__init__(self)
        Constructor.__init__(self)
        Resolver.__init__(self)
    
    def omap_constructor(self, loader, node):
        return loader.construct_pairs(node)