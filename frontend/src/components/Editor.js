import React from 'react';
import { Editor as MonacoEditor } from '@monaco-editor/react';

const Editor = ({ code, setCode }) => {
  return (
    <MonacoEditor
      height="400px"
      language="java"
      value={code}
      onChange={(value) => setCode(value || '')}
    />
  );
};

export default Editor;
