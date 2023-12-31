import styled, { css } from 'styled-components';

const StyleLogin = styled.div.attrs<any>((props) => ({}))`
  ${(props) => {
    return css`
      width: 100vw;
      height: 100vh;
      /* position: fixed;
      bottom: 70px;
      z-index: 3;
      display: flex;
      gap: 20px;
      width: 100vw;
      overflow-x: scroll;
      white-space: nowrap;
      flex-direction: row; */
    `;
  }};
`;
const HeaderStyle = styled.div.attrs<any>((props) => ({}))`
  ${(props) => {
    return css`
      font-size: 36px;
      display: flex;
      width: 100vw;
      height: 15%;
      justify-content: center;
      align-items: flex-end;
    `;
  }};
`;

const BodyStyle = styled.div.attrs<any>((props) => ({}))`
  ${(props) => {
    return css`
      display: flex;
      width: 100%;
      height: 60%;
      flex-direction: column;
      align-items: center;
      justify-content: center;
    `;
  }};
`;

const FooterStyle = styled.div.attrs<any>((props) => ({}))`
  ${(props) => {
    return css`
      width: 100%;
      height: 25%;
      display: flex;
      justify-content: center;
      flex-direction: column;
      align-items: center;
    `;
  }};
`;

const InputWrapper = styled.div.attrs<any>((props) => ({}))`
  ${(props) => {
    const marginTop = props.margintop || '0px';
    return css`
      display: flex;
      align-items: center;
      padding: 0px 30px;
      width: 100%;
      height: 90px;
    `;
  }};
`;

const ButtonWrapper = styled.div.attrs<any>((props) => ({}))`
  ${(props) => {
    const marginTop = props.marginTop || '0px';
    return css`
      width: 100%;
      height: 55px;
      padding: 5px 30px;
    `;
  }};
`;

const ButtonList = styled.div.attrs<any>((props) => ({}))`
  ${(props) => {
    return css`
      width: 100%;
      padding-top: 20px;
    `;
  }};
`;

export { StyleLogin, HeaderStyle, InputWrapper, BodyStyle, FooterStyle, ButtonWrapper, ButtonList };
