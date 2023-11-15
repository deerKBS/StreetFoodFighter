import styled, { css } from 'styled-components';

const OrderDetailStyle = styled.div.attrs<any>((props) => ({}))`
  ${(props) => {
    const gray = props.theme.colors.lightgray;

    return css`
      background-color: ${gray};
      height: 100vh;
      overflow: hidden;
    `;
  }};
`;

const OrderInfo = styled.div.attrs<any>((props) => ({}))`
  ${(props) => {
    const white = props.theme.colors.white;

    return css`
      background-color: ${white};
      padding: 20px;
      margin-top: 10px;
    `;
  }};
`;

const OrderState = styled.div.attrs<any>((props) => ({}))`
  ${(props) => {
    const color = props.theme.colors.main;

    return css`
      font-size: 16px;
      padding: 5px 0px;
      color: ${color};
    `;
  }};
`;

const StoreName = styled.div`
  font-size: 20px;
  display: flex;
  align-items: center;
  padding: 5px 0px;
`;

const StoreAddress = styled.div`
  padding: 5px 0px;
  font-size: 18px;
`;

const StoreTextLine = styled.div.attrs<any>((props) => ({}))`
  ${(props) => {
    const font = props.theme.fonts.air;
    return css`
      font-size: 16px;
      padding: 3px 0px;
      span {
        font-family: ${font};
      }
    `;
  }};
`;
const ReceiptTabble = styled.div.attrs<any>((props) => ({}))`
  ${(props) => {
    const white = props.theme.colors.white;

    return css`
      background-color: ${white};
      padding: 20px;
      margin-top: 10px;
    `;
  }};
`;
export { ReceiptTabble, StoreTextLine, StoreAddress, StoreName, OrderState, OrderInfo, OrderDetailStyle };
