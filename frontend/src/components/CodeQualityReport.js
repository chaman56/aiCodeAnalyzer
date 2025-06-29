import React from 'react';
import './CodeQualityReport.css';

const CodeQualityReport = ({ report }) => {
    if (!report || !report.metrics) {
        return <p>No code quality report available.</p>;
    }

    const getScoreClass = (score) => {
        if (score >= 80) return 'score-green';
        if (score >= 50) return 'score-yellow';
        return 'score-red';
    };

    return (
        <div className="code-quality-report">
            <h4>Code Quality Report</h4>
            <table>
                <thead>
                    <tr>
                        <th>Metric</th>
                        <th>Score</th>
                        <th>Suggestions</th>
                    </tr>
                </thead>
                <tbody>
                    {report.metrics.map((metric, index) => (
                        <tr key={index}>
                            <td>{metric.metricName}</td>
                            <td className={getScoreClass(metric.score)}>{metric.score}/100</td>
                            <td>
                                <ul>
                                    {metric.suggestions.map((suggestion, i) => (
                                        <li key={i}>{suggestion}</li>
                                    ))}
                                </ul>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default CodeQualityReport;
