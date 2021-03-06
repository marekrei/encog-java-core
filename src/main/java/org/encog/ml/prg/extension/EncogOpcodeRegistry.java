package org.encog.ml.prg.extension;

import java.util.HashMap;
import java.util.Map;

import org.encog.ml.prg.EncogProgramContext;
import org.encog.ml.prg.exception.EncogEPLError;

public enum EncogOpcodeRegistry {
	INSTANCE;
	
	private Map<Integer,ProgramExtensionTemplate> registry = new HashMap<Integer,ProgramExtensionTemplate>();

	private EncogOpcodeRegistry() {
		add(StandardExtensions.EXTENSION_VAR_SUPPORT);
		add(StandardExtensions.EXTENSION_CONST_FLOAT); 
		add(StandardExtensions.EXTENSION_CONST_BOOL);
		add(StandardExtensions.EXTENSION_CONST_INT);
		add(StandardExtensions.EXTENSION_CONST_STRING);
		add(StandardExtensions.EXTENSION_NEG);
		add(StandardExtensions.EXTENSION_ADD);
		add(StandardExtensions.EXTENSION_SUB);
		add(StandardExtensions.EXTENSION_MUL);
		add(StandardExtensions.EXTENSION_DIV);
		add(StandardExtensions.EXTENSION_POWER);
		add(StandardExtensions.EXTENSION_AND);
		add(StandardExtensions.EXTENSION_OR);
		add(StandardExtensions.EXTENSION_EQUAL);
		add(StandardExtensions.EXTENSION_GT); 
		add(StandardExtensions.EXTENSION_LT); 
		add(StandardExtensions.EXTENSION_GTE);
		add(StandardExtensions.EXTENSION_LTE);
		add(StandardExtensions.EXTENSION_ABS);
		add(StandardExtensions.EXTENSION_ACOS); 
		add(StandardExtensions.EXTENSION_ASIN);
		add(StandardExtensions.EXTENSION_ATAN);
		add(StandardExtensions.EXTENSION_ATAN2);
		add(StandardExtensions.EXTENSION_CEIL);
		add(StandardExtensions.EXTENSION_COS); 
		add(StandardExtensions.EXTENSION_COSH);
		add(StandardExtensions.EXTENSION_EXP); 
		add(StandardExtensions.EXTENSION_FLOOR);
		add(StandardExtensions.EXTENSION_LOG);
		add(StandardExtensions.EXTENSION_LOG10);
		add(StandardExtensions.EXTENSION_MAX);
		add(StandardExtensions.EXTENSION_MIN); 
		add(StandardExtensions.EXTENSION_POWFN);
		add(StandardExtensions.EXTENSION_RANDOM);
		add(StandardExtensions.EXTENSION_ROUND);
		add(StandardExtensions.EXTENSION_SIN);
		add(StandardExtensions.EXTENSION_SINH);
		add(StandardExtensions.EXTENSION_SQRT);
		add(StandardExtensions.EXTENSION_TAN);
		add(StandardExtensions.EXTENSION_TANH);
		add(StandardExtensions.EXTENSION_TODEG);
		add(StandardExtensions.EXTENSION_TORAD);
		add(StandardExtensions.EXTENSION_LENGTH);
		add(StandardExtensions.EXTENSION_FORMAT);
		add(StandardExtensions.EXTENSION_LEFT);
		add(StandardExtensions.EXTENSION_RIGHT); 
		add(StandardExtensions.EXTENSION_CINT); 
		add(StandardExtensions.EXTENSION_CFLOAT);
		add(StandardExtensions.EXTENSION_CSTR); 
		add(StandardExtensions.EXTENSION_CBOOL);
		add(StandardExtensions.EXTENSION_IFF); 
		add(StandardExtensions.EXTENSION_CLAMP); 
	}
		

	
	
	public void add(ProgramExtensionTemplate ext) {
		registry.put((int)ext.getOpcode(),ext);
	}




	public void register(EncogProgramContext context, int opcode) {
		if( !this.registry.containsKey(opcode) ) {
			throw new EncogEPLError("Unknown opcode: " + opcode);
		}
		ProgramExtensionTemplate temp = this.registry.get(opcode);
		context.getFunctions().addExtension(temp);
	}
	
}
