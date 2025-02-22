package com.seaofnodes.simple.node;

import com.seaofnodes.simple.type.Type;
import com.seaofnodes.simple.type.TypeBot;
import com.seaofnodes.simple.type.TypeTuple;

/**
 * The Start node represents the start of the function.  For now, we do not
 * have any inputs to Start because our function does not yet accept
 * parameters.  When we add parameters the value of Start will be a tuple, and
 * will require Projections to extract the values.  We discuss this in detail
 * in Chapter 9: Functions and Calls.
 */
public class StartNode extends MultiNode implements Control {

    final TypeTuple _args;
    
    public StartNode(Type[] args) {
        super();
        _args = new TypeTuple(args);
    }

    @Override
    public String label() { return "Start"; }

    @Override
    StringBuilder _print1(StringBuilder sb) {
      return sb.append(label());
    }
  
    @Override
    public TypeTuple compute() { return _args; }

    @Override
    public Node idealize() { return null; }

    
    @Override 
    public ProjNode proj( int idx ) {
        for( Node proj : _outputs )
            if( ((ProjNode)proj)._idx==idx )
                return (ProjNode)proj;
        return null;
    }
  
}
