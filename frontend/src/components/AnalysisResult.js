import React from 'react';
import ReactMarkdown from 'react-markdown';
import Tabs from './Tabs';
import CodeQualityReport from './CodeQualityReport';
import './AnalysisResult.css';

const AnalysisResult = ({ analysis, applyFix, downloadReport }) => {
  // Defensively check if analysis and its properties exist
  if (!analysis) {
    return <div>Loading analysis...</div>;
  }

  const getIssueClass = (issue) => {
    if (issue.toLowerCase().includes('error')) {
        return 'issue-error';
    }
    return 'issue-warning';
  };

  return (
    <div>
      <h2>Analysis Report</h2>
      <Tabs>
        <div label="Code Quality">
          <CodeQualityReport report={analysis.codeQualityReport} />
        </div>
        <div label="Generated Fix">
          {analysis.generatedFix ? (
            <div>
              <button onClick={applyFix}>Apply Fix</button>
              <ReactMarkdown>{analysis.generatedFix}</ReactMarkdown>
            </div>
          ) : <p>No fix generated.</p>}
        </div>
        {/* <div label="AI Suggestions">
          {analysis.suggestions && analysis.suggestions.length > 0 ? (
            <ul>
              {analysis.suggestions.map((suggestion, index) => (
                <li key={index}><ReactMarkdown>{suggestion}</ReactMarkdown></li>
              ))}
            </ul>
          ) : <p>No suggestions available.</p>}
        </div> */}
        <div label="Generated Test Cases">
          {analysis.generatedTestCases && analysis.generatedTestCases.length > 0 ? (
            <ul>
              {analysis.generatedTestCases.map((testCase, index) => (
                <li key={index}><ReactMarkdown>{testCase}</ReactMarkdown></li>
              ))}
            </ul>
          ) : <p>No test cases generated.</p>}
        </div>
        <div label="Issues">
          {analysis.issues && analysis.issues.length > 0 ? (
            <ul>
              {analysis.issues.map((issue, index) => (
                <li key={index} className={getIssueClass(issue)}>{issue}</li>
              ))}
            </ul>
          ) : <p>No issues found.</p>}
        </div>
      </Tabs>
      <button onClick={downloadReport}>Download Report</button>
    </div>
  );
};

export default AnalysisResult;
