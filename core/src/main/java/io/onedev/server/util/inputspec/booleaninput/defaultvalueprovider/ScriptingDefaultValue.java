package io.onedev.server.util.inputspec.booleaninput.defaultvalueprovider;

import org.hibernate.validator.constraints.NotEmpty;

import io.onedev.server.util.GroovyUtils;
import io.onedev.server.util.editable.annotation.Editable;
import io.onedev.server.util.editable.annotation.Multiline;
import io.onedev.server.util.editable.annotation.OmitName;
import io.onedev.server.util.editable.annotation.Script;

@Editable(order=400, name="Evaluate script to get default value")
public class ScriptingDefaultValue implements DefaultValueProvider {

	private static final long serialVersionUID = 1L;

	private String script;

	@Editable(description="Groovy script to be evaluated. It should return a <i>boolean</i> value. "
			+ "Check <a href='$onedev.docLink/scripting.md' target='_blank'>scripting help</a> for details")
	@NotEmpty
	@Script
	@OmitName
	@Multiline
	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public boolean getDefaultValue() {
		return (boolean) GroovyUtils.evalScript(getScript());
	}

}