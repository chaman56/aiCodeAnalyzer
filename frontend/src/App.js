import React, { useState } from 'react';
import logo from './logo.svg';
import Editor from './components/Editor';
import AnalysisResult from './components/AnalysisResult';
import axios from 'axios';
import './App.css';

function App() {
  const [analysis, setAnalysis] = useState(null);
  const [code, setCode] = useState('// Enter your Java code here');

  const analyzeCode = async () => {
    try {
      const response = await axios.post('/api/analyze', { code });
      console.log("response data", response.data)
      setAnalysis(response.data);
    } catch (error) {
      console.error('Error analyzing code:', error);
    }
  };

  const applyFix = () => {
    if (analysis && analysis.autoFix) {
      setCode(analysis.autoFix);
    }
  };

  const downloadReport = async () => {
    if (!analysis) return;

    try {
      const response = await axios.post('/api/pdf/generate', analysis, {
        responseType: 'blob',
      });
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', 'analysis-report.pdf');
      document.body.appendChild(link);
      link.click();
    } catch (error) {
      console.error('Error downloading report:', error);
    }
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>AI Code Analyzer</h1>
      </header>
      <main className="main-content">
        <div className="editor-container">
          <Editor code={code} setCode={setCode} />
          <button className="analyze-button" onClick={analyzeCode}>Analyze Code</button>
        </div>
        <div className="analysis-container">
          {analysis && <AnalysisResult analysis={analysis} applyFix={applyFix} downloadReport={downloadReport} />}
        </div>
      </main>
    </div>
  );
}

export default App;
