package com.ai.analyzer.service;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ParseResult;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.Problem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaticAnalysisService {

    public List<String> analyze(String code) {
        List<String> issues = new ArrayList<>();
        // Configure JavaParser for a specific Java version
        ParserConfiguration parserConfiguration = new ParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17);
        JavaParser javaParser = new JavaParser(parserConfiguration);
        ParseResult<CompilationUnit> parseResult = javaParser.parse(code);

        if (!parseResult.isSuccessful()) {
            issues.add("Code contains parse errors:");
            for (Problem problem : parseResult.getProblems()) {
                String location = problem.getLocation().map(tokenRange -> {
                    return "line " + tokenRange.getBegin().getRange().map(r -> String.valueOf(r.begin.line)).orElse("unknown");
                }).orElse("unknown location");
                issues.add("- " + problem.getMessage() + " at " + location);
            }
            return issues;
        }

        CompilationUnit cu = parseResult.getResult().get();

        // Visitor to detect code smells
        cu.accept(new CodeSmellVisitor(), issues);

        return issues;
    }

    private static class CodeSmellVisitor extends VoidVisitorAdapter<List<String>> {

        @Override
        public void visit(MethodDeclaration n, List<String> issues) {
            super.visit(n, issues);
            if (n.getBody().isPresent() && n.getBody().get().getStatements().size() > 20) {
                issues.add("Long Method: " + n.getNameAsString() + " has more than 20 statements at line " + n.getRange().get().begin.line);
            }
            if (n.getParameters().size() > 5) {
                issues.add("Long Parameter List: " + n.getNameAsString() + " has more than 5 parameters at line " + n.getRange().get().begin.line);
            }
        }

        @Override
        public void visit(CatchClause n, List<String> arg) {
            super.visit(n, arg);
            if (n.getBody().getStatements().isEmpty()) {
                arg.add("Empty Catch Block at line " + n.getRange().get().begin.line);
            }
        }

        @Override
        public void visit(IntegerLiteralExpr n, List<String> arg) {
            super.visit(n, arg);
            // Simple check for magic numbers, ignoring declarations
            if (!(n.getParentNode().orElse(null) instanceof FieldDeclaration)) {
                arg.add("Magic Number: " + n.getValue() + " at line " + n.getRange().get().begin.line);
            }
        }

        @Override
        public void visit(MethodCallExpr n, List<String> arg) {
            super.visit(n, arg);
            if (n.getScope().isPresent() && n.getScope().get().toString().equals("System.out") && n.getNameAsString().equals("println")) {
                arg.add("Use of System.out.println at line " + n.getRange().get().begin.line + ". Consider using a logger.");
            }
        }

        @Override
        public void visit(StringLiteralExpr n, List<String> arg) {
            super.visit(n, arg);
            // Simple check for hardcoded strings, can be refined
            if (n.asString().length() > 20) { // Arbitrary length to avoid flagging all strings
                arg.add("Hardcoded String Literal at line " + n.getRange().get().begin.line + ": \"" + n.asString() + "\"");
            }
        }
    }
}
