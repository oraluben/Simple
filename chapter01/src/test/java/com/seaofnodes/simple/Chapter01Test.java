package com.seaofnodes.simple;

import com.seaofnodes.simple.node.ConstantNode;
import com.seaofnodes.simple.node.Node;
import com.seaofnodes.simple.node.ReturnNode;
import com.seaofnodes.simple.node.StartNode;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Chapter01Test {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testSimpleProgram() {
        Parser parser = new Parser("return 1;");
        ReturnNode ret = parser.parse();
        StartNode start = Parser.START;

        assertEquals(start, ret.ctrl());
        Node expr = ret.expr();
        if (expr instanceof ConstantNode con) {
            assertEquals(start, con.in(0));
            assertEquals(1, con._value);
        } else {
            fail();
        }
    }

    @Test
    public void testZero() {
        Parser parser = new Parser("return 0;");
        parser.parse();
        StartNode start = Parser.START;
        for( Node use : start._outputs )
            if( use instanceof ConstantNode con )
                assertEquals(0,con._value);
    }

    @Test
    public void testBad1() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Syntax error, expected return: ret");
        new Parser("ret").parse();
    }

    @Test
    public void testBad2() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Syntax error: integer values cannot start with '0'");
        new Parser("return 0123;").parse();
    }

    @Test
    public void testBad3() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Syntax error, expected integer literal");
        new Parser("return --12;").parse();
    }

    @Test
    public void testBad4() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Syntax error, expected ;:");
        new Parser("return 100").parse();
    }

    // Negative numbers require unary operator support that is not in scope
    @Test
    public void testBad5() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Syntax error, expected integer literal");
        new Parser("return -100;").parse();
    }
}
