package grammar.handler;

import grammar.syntax.Analyser;

import java.util.List;
import java.util.Map;

public interface Handler {


    Map<String,List<String>> execute(String command, Analyser analyser);



}
